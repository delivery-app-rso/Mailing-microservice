package si.fri.rso.mailingmicroservice.services.beans;

import si.fri.rso.mailingmicroservice.lib.Attachment;
import si.fri.rso.mailingmicroservice.lib.Mail;
import si.fri.rso.mailingmicroservice.lib.MailingDto;
import si.fri.rso.mailingmicroservice.models.converters.AttachmentConverter;
import si.fri.rso.mailingmicroservice.models.converters.MailConverter;
import si.fri.rso.mailingmicroservice.models.entities.AttachmentEntity;
import si.fri.rso.mailingmicroservice.models.entities.MailEntity;
import si.fri.rso.mailingmicroservice.services.mailing.EmailFactory;
import si.fri.rso.mailingmicroservice.services.mailing.SendEmail;
import si.fri.rso.mailingmicroservice.services.mailing.emails.Email;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.eclipse.microprofile.metrics.annotation.Timed;
import org.json.JSONObject;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@RequestScoped
public class MailingBean {
    private Logger log = Logger.getLogger(MailingBean.class.getName());

    @Inject
    private EntityManager em;

    @Inject
    private SendEmail sendEmail;

    @Inject
    private EmailFactory emailFactory;

    public List<Mail> getMails() {
        TypedQuery<MailEntity> query = em.createNamedQuery(
                "MailEntity.getAll", MailEntity.class);

        List<MailEntity> resultList = query.getResultList();
        return resultList.stream().map(MailConverter::toDto).collect(Collectors.toList());
    }

    public List<Attachment> getAttachements() {
        TypedQuery<AttachmentEntity> query = em.createNamedQuery(
                "AttachmentEntity.getAll", AttachmentEntity.class);

        List<AttachmentEntity> resultList = query.getResultList();
        return resultList.stream().map(AttachmentConverter::toDto).collect(Collectors.toList());
    }

    @Timed
    public Mail sendEmail(MailingDto mailingDto) {
        Email email = this.emailFactory.createEmail(mailingDto);
        this.persistEntity(MailConverter.toEntity(email.getEmail()));

        log.log(Level.INFO, "Sending email to " + email.getEmail().getRecipient());
        this.sendEmail.send(email.getEmail());

        return email.getEmail();
    }

    private <T> void persistEntity(T entity) {
        try {
            beginTx();
            em.persist(entity);
            commitTx();
            log.log(Level.INFO, "Persisted " + entity.toString());
        } catch (Exception e) {
            rollbackTx();
            log.log(Level.INFO, "Failed to persist entity. Rolling back.");
        }
    }

    private void beginTx() {
        if (!em.getTransaction().isActive()) {
            em.getTransaction().begin();
        }
    }

    private void commitTx() {
        if (em.getTransaction().isActive()) {
            em.getTransaction().commit();
        }
    }

    private void rollbackTx() {
        if (em.getTransaction().isActive()) {
            em.getTransaction().rollback();
        }
    }
}