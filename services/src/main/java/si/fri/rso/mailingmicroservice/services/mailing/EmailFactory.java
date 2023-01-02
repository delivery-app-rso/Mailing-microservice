package si.fri.rso.mailingmicroservice.services.mailing;

import si.fri.rso.mailingmicroservice.lib.MailingDto;
import si.fri.rso.mailingmicroservice.services.mailing.emails.DeliveredEmail;
import si.fri.rso.mailingmicroservice.services.mailing.emails.DeliveryStartEmail;
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

    public EmailFactory() {
    }

    public Email createEmail(MailingDto mailingDto) {
        switch (mailingDto.getType()) {
            case "invoice":
                return new InvoiceEmail(templateEngine, mailingDto.getInvoiceData(), mailingDto.getUserData());
            case "registration":
                return new RegistrationEmail(templateEngine, mailingDto.getUserData());
            case "delivery_start":
                return new DeliveryStartEmail(templateEngine, mailingDto.getUserData(), mailingDto.getDeliveryData());
            case "delivered":
                return new DeliveredEmail(templateEngine, mailingDto.getUserData(), mailingDto.getDeliveryData());
            default:
                return null;
        }
    }
}