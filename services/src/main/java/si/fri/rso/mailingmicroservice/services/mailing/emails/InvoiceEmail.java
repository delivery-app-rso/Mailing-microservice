package si.fri.rso.mailingmicroservice.services.mailing.emails;

import si.fri.rso.mailingmicroservice.lib.Attachment;
import si.fri.rso.mailingmicroservice.services.templates.TemplateEngine;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

public class InvoiceEmail extends Email {
    private Map<String, String> invoiceData;

    private Map<String, String> userData;

    public InvoiceEmail(TemplateEngine templateEngine, Map<String, String> invoiceData, Map<String, String> userData) {
        super(templateEngine);

        this.userData = userData;
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
        Map<String, String> dataModel = new HashMap<>();
        dataModel.put("user", this.userData.get("name"));
        dataModel.put("link", "http://127.0.0.1");
        dataModel.put("item", this.invoiceData.get("item"));
        dataModel.put("amount", this.invoiceData.get("amount"));
        String mailBody = this.templateEngine.getTemplateHTML(this.getEmailTemplateName() + ".html", dataModel);

        this.email = this.generateMail("Delivery invoice", this.userData.get("email"), mailBody);
        this.email.addAttachement(this.createAttachement());
    }

    @Override
    public String getEmailTemplateName() {
        return "invoice";
    }
}