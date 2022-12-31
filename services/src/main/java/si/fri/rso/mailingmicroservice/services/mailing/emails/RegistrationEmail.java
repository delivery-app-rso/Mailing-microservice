package si.fri.rso.mailingmicroservice.services.mailing.emails;

import si.fri.rso.mailingmicroservice.services.templates.TemplateEngine;

import java.util.HashMap;
import java.util.Map;

public class RegistrationEmail extends Email {

    public RegistrationEmail(TemplateEngine templateEngine) {
        super(templateEngine);
        this.generateEmail();
    }

    @Override
    public void generateEmail() {
        // TODO: Get user ID from query and fetch user data from users microservice
        Map<String, String> dataModel = new HashMap<>();
        dataModel.put("user", "Test User");
        dataModel.put("link", "http://127.0.0.1");

        String mailBody = this.templateEngine.getTemplateHTML(this.getEmailTemplateName() + ".html", dataModel);
        this.email = this.generateMail("Registration successfull", "testuser@gmail.com", mailBody);
    }

    @Override
    public String getEmailTemplateName() {
        return "registration_success";
    }
}