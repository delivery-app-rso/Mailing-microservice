package si.fri.rso.mailingmicroservice.services.mailing.emails;

import si.fri.rso.mailingmicroservice.lib.Attachment;
import si.fri.rso.mailingmicroservice.services.templates.TemplateEngine;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

public class InvoiceEmail extends Email {

    public InvoiceEmail(TemplateEngine templateEngine) {
        super(templateEngine);
    }

    private Attachment createAttachement() {
        //TODO: Fetch data from invoice microservice
        Attachment attachment = new Attachment();
        attachment.setTitle("Test-user-invoice.pdf");
        attachment.setType("Invoice");

        return attachment;
    }

    @Override
    public void generateEmail() {
        // TODO: Get user ID from query and fetch data from microservices
        Map<String, String> dataModel = new HashMap<>();
        dataModel.put("user", "Test User");
        dataModel.put("link", "http://127.0.0.1");
        dataModel.put("item", "Test Item");
        dataModel.put("amount", "99,99");
        String mailBody = this.templateEngine.getTemplateHTML(this.getEmailTemplateName() + ".html", dataModel);

        this.email = this.generateMail("Test subject", "testuser@gmail.com", mailBody);
        this.email.addAttachement(this.createAttachement());
    }

    @Override
    public String getEmailTemplateName() {
        return "invoice";
    }
}