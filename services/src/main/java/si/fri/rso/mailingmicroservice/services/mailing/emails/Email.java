package si.fri.rso.mailingmicroservice.services.mailing.emails;

import si.fri.rso.mailingmicroservice.lib.Mail;
import si.fri.rso.mailingmicroservice.services.templates.TemplateEngine;

import javax.inject.Inject;
import java.util.logging.Logger;

public abstract class Email {

    protected Mail email;

    protected Logger log = Logger.getLogger(Email.class.getName());

    protected TemplateEngine templateEngine;

    public Email(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
        this.generateEmail();
    }

    protected Mail generateMail(String subject, String recipient, String body) {
        Mail mail = new Mail();

        mail.setBody(body);
        mail.setRecipient(recipient);
        mail.setSender("deliverio@gmail.com");  //TODO: Remove magic string with configuration property
        mail.setSubject(subject);

        return mail;
    }

    public Mail getEmail() {
        return email;
    }

    // TODO: Add User parameter
    public abstract void generateEmail();

    public abstract String getEmailTemplateName();
}
