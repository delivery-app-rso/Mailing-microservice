package si.fri.rso.mailingmicroservice.services.mailing.emails;

import si.fri.rso.mailingmicroservice.lib.Attachment;
import si.fri.rso.mailingmicroservice.lib.MailingDto;
import si.fri.rso.mailingmicroservice.services.templates.TemplateEngine;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

import org.json.JSONObject;

public class InvoiceEmail extends Email {
    private JSONObject itemJsonObject;

    private Map<String, String> invoiceData;

    public InvoiceEmail(TemplateEngine templateEngine, Map<String, String> invoiceData, JSONObject itemJsonObject) {
        super(templateEngine);

        this.itemJsonObject = itemJsonObject;
        this.invoiceData = invoiceData;

        this.generateEmail();
    }

    private Attachment createAttachement() {
        Attachment attachment = new Attachment();
        attachment.setTitle(this.invoiceData.get("filename"));
        attachment.setType("Invoice");

        return attachment;
    }

    @Override
    public void generateEmail() {
        // TODO: Get user ID from query and fetch data from microservices
        Map<String, String> dataModel = new HashMap<>();
        dataModel.put("user", "Test User");
        dataModel.put("link", "http://127.0.0.1");
        dataModel.put("item", this.itemJsonObject.getString("name"));
        dataModel.put("amount", this.invoiceData.get("amount"));
        String mailBody = this.templateEngine.getTemplateHTML(this.getEmailTemplateName() + ".html", dataModel);

        this.email = this.generateMail("Delivery invoice", "testuser@gmail.com", mailBody);
        this.email.addAttachement(this.createAttachement());
    }

    @Override
    public String getEmailTemplateName() {
        return "invoice";
    }
}