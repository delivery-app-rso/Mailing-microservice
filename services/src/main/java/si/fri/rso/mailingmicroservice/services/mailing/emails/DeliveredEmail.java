package si.fri.rso.mailingmicroservice.services.mailing.emails;

import si.fri.rso.mailingmicroservice.services.templates.TemplateEngine;

import java.util.HashMap;
import java.util.Map;

public class DeliveredEmail extends Email {

    private Map<String, String> usersData;

    private Map<String, String> deliveryData;

    public DeliveredEmail(TemplateEngine templateEngine, Map<String, String> usersData,
            Map<String, String> deliveryData) {
        super(templateEngine);
        this.usersData = usersData;
        this.deliveryData = deliveryData;
        this.generateEmail();
    }

    @Override
    public void generateEmail() {
        Map<String, String> dataModel = new HashMap<>();
        dataModel.put("user", usersData.get("name"));
        dataModel.put("item", deliveryData.get("item"));

        String mailBody = this.templateEngine.getTemplateHTML(this.getEmailTemplateName() + ".html", dataModel);
        this.email = this.generateMail("Delivery for " + deliveryData.get("item") + " complete", usersData.get("email"),
                mailBody);
    }

    @Override
    public String getEmailTemplateName() {
        return "delivered";
    }
}