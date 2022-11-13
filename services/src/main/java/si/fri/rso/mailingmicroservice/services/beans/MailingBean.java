package si.fri.rso.mailingmicroservice.services.beans;

import si.fri.rso.mailingmicroservice.lib.Attachement;
import si.fri.rso.mailingmicroservice.lib.Mail;
import si.fri.rso.mailingmicroservice.models.converters.AttachmentConverter;
import si.fri.rso.mailingmicroservice.models.converters.MailConverter;
import si.fri.rso.mailingmicroservice.models.entities.AttachmentEntity;
import si.fri.rso.mailingmicroservice.models.entities.MailEntity;
import si.fri.rso.mailingmicroservice.services.mailing.SendEmail;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.UriInfo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import com.kumuluz.ee.rest.beans.QueryParameters;
import com.kumuluz.ee.rest.utils.JPAUtils;

@RequestScoped
public class MailingBean {
    private Logger log = Logger.getLogger(MailingBean.class.getName());

    @Inject
    private EntityManager em;

    @Inject
    private SendEmail sendEmail;

    public List<Mail> getMails() {
        TypedQuery<MailEntity> query = em.createNamedQuery(
                "MailEntity.getAll", MailEntity.class);

        List<MailEntity> resultList = query.getResultList();
        return resultList.stream().map(MailConverter::toDto).collect(Collectors.toList());
    }

    public List<Attachement> getAttachements() {
        TypedQuery<AttachmentEntity> query = em.createNamedQuery(
                "AttachmentEntity.getAll", AttachmentEntity.class);

        List<AttachmentEntity> resultList = query.getResultList();
        return resultList.stream().map(AttachmentConverter::toDto).collect(Collectors.toList());
    }

    public AttachmentEntity createAttachement() {
        AttachmentEntity attachmentEntity = new AttachmentEntity();
        attachmentEntity.setTitle("Testuser-invoice.pdf");
        attachmentEntity.setType("Invoice");

        this.persistEntity(attachmentEntity);

        return attachmentEntity;
    }

    public Mail sendEmail() {
        Map<String, String> dataModel = new HashMap<>();
        dataModel.put("user", "Test user");
        String mailBody = this.sendEmail.getTemplateData("invoice.html", dataModel);

        HashMap<String, String> mailData = new HashMap<>();
        mailData.put("subject", "Test");
        mailData.put("recipient", "testemail@gmail.com");
        mailData.put("body", mailBody);

        MailEntity mail = this.sendEmail.generateMail(mailData);
        mail.addAttachement(this.createAttachement());
        this.persistEntity(mail);

        this.sendEmail.send(MailConverter.toDto(mail));

        log.log(Level.INFO, "Sending email to " + mail.getRecipient());
        return MailConverter.toDto(mail);
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