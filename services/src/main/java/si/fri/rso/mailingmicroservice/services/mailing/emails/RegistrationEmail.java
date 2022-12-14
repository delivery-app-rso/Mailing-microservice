package si.fri.rso.mailingmicroservice.services.mailing.emails;

import si.fri.rso.mailingmicroservice.services.templates.TemplateEngine;

import java.util.HashMap;
import java.util.Map;

public class RegistrationEmail extends Email {

    private Map<String, String> usersData;

    public RegistrationEmail(TemplateEngine templateEngine, Map<String, String> usersData) {
        super(templateEngine);
        this.usersData = usersData;
        this.generateEmail();
    }

    @Override
    public void generateEmail() {

        Map<String, String> dataModel = new HashMap<>();
        dataModel.put("user", usersData.get("name"));
        dataModel.put("link", "http://127.0.0.1");

        String mailBody = this.templateEngine.getTemplateHTML(this.getEmailTemplateName() + ".html", dataModel);
        this.email = this.generateMail("Registration successfull", usersData.get("email"), mailBody);
    }

    @Override
    public String getEmailTemplateName() {
        return "registration_success";
    }
}