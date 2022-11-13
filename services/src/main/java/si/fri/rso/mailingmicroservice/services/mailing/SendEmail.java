package si.fri.rso.mailingmicroservice.services.mailing;

import si.fri.rso.mailingmicroservice.lib.Mail;
import si.fri.rso.mailingmicroservice.models.entities.MailEntity;
import si.fri.rso.mailingmicroservice.services.templates.TemplateEngine;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import io.github.cdimascio.dotenv.Dotenv;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@RequestScoped
public class SendEmail {
    private final Session session;
    private final String sender;

    @Inject
    TemplateEngine templateEngine;

    public SendEmail() {
        Dotenv dotenv = Dotenv.load();
        Properties prop = new Properties();

        prop.put("mail.smtp.auth", false);
        prop.put("mail.smtp.starttls.enable", "true");
        prop.put("mail.smtp.host", dotenv.get("SMTP_HOST"));
        prop.put("mail.smtp.port", dotenv.get("SMTP_PORT"));
        prop.put("mail.smtp.ssl.trust", dotenv.get("SMTP_HOST"));

        this.sender = dotenv.get("SMTP_SENDER_EMAIL");
        this.session = Session.getInstance(prop);
    }

    public MailEntity generateMail(HashMap<String, String> mailData) {
        MailEntity mail = new MailEntity();

        mail.setBody(mailData.get("body"));
        mail.setRecipient(mailData.get("recipient"));
        mail.setSender(this.sender);
        mail.setSubject(mailData.get("subject"));

        return mail;
    }

    public String getTemplateData(String templateName, Map<String, String> dataModel) {
        return this.templateEngine.getTemplateHTML(templateName, dataModel);
    }

    public void send(Mail mail) {
        try {
            MimeBodyPart mimeBodyPartWithStyledText = new MimeBodyPart();
            mimeBodyPartWithStyledText.setContent(mail.getBody(), "text/html; charset=utf-8");

            Message message = new MimeMessage(this.session);
            message.setFrom(new InternetAddress(mail.getSender()));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(mail.getRecipient()));
            message.setSubject(mail.getSubject());

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(mimeBodyPartWithStyledText);

            message.setContent(multipart);

            Transport.send(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}