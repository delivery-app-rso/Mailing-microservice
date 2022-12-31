package si.fri.rso.mailingmicroservice.services.mailing;

import si.fri.rso.mailingmicroservice.lib.MailingDto;
import si.fri.rso.mailingmicroservice.services.beans.ServicesBean;
import si.fri.rso.mailingmicroservice.services.mailing.emails.Email;
import si.fri.rso.mailingmicroservice.services.mailing.emails.InvoiceEmail;
import si.fri.rso.mailingmicroservice.services.mailing.emails.RegistrationEmail;
import si.fri.rso.mailingmicroservice.services.templates.TemplateEngine;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import org.json.JSONArray;
import org.json.JSONObject;

@RequestScoped
public class EmailFactory {

    @Inject
    TemplateEngine templateEngine;

    @Inject
    private ServicesBean servicesBean;

    public EmailFactory() {
    }

    public Email createEmail(MailingDto mailingDto) {
        switch (mailingDto.getType()) {
            case "invoice":
                JSONObject itemJsonObject = this.servicesBean
                        .getItem(Integer.parseInt(mailingDto.getInvoiceData().get("itemId")));

                return new InvoiceEmail(templateEngine, mailingDto.getInvoiceData(), itemJsonObject);
            case "registration":
                return new RegistrationEmail(templateEngine);
            default:
                return null;
        }
    }
}