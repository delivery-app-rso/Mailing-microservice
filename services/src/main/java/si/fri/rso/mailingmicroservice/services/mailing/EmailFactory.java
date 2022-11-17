package si.fri.rso.mailingmicroservice.services.mailing;

import si.fri.rso.mailingmicroservice.services.mailing.emails.Email;
import si.fri.rso.mailingmicroservice.services.mailing.emails.InvoiceEmail;
import si.fri.rso.mailingmicroservice.services.mailing.emails.RegistrationEmail;
import si.fri.rso.mailingmicroservice.services.templates.TemplateEngine;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

@RequestScoped
public class EmailFactory {

    @Inject
    TemplateEngine templateEngine;

    public EmailFactory() {}

    public Email createEmail(String type) {
        switch (type) {
            case "invoice":
                return new InvoiceEmail(templateEngine);
            case "registration":
                return new RegistrationEmail(templateEngine);
            default:
                return null;
        }
    }
}