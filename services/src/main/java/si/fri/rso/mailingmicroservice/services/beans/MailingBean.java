package si.fri.rso.mailingmicroservice.services.beans;

import si.fri.rso.mailingmicroservice.lib.Mail;
import si.fri.rso.mailingmicroservice.models.converters.MailConverter;
import si.fri.rso.mailingmicroservice.models.entities.MailEntity;
import si.fri.rso.mailingmicroservice.services.mailing.SendEmail;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.UriInfo;
import java.util.List;
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

    public Mail sendEmail() {
        log.log(Level.INFO, "Sending email to tesd@gmail.com");
        
        Mail mail = new Mail();
        mail.setBody("test email");
        mail.setRecipient("test@recipient.com");
        mail.setSender("test@sender.com");
        mail.setSubject("test subject from sender!");
        
        this.createMail(mail);
        
        this.sendEmail.send(mail);
        return mail;
    }

    private Mail createMail(Mail mail) {
        MailEntity mailEntity = MailConverter.toEntity(mail);

        try {
            beginTx();
            em.persist(mailEntity);
            commitTx();
        }
        catch (Exception e) {
            rollbackTx();
        }

        if (mailEntity.getId() == null) {
            throw new RuntimeException("Mail Entity was not persisted");
        }

        return mail;
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