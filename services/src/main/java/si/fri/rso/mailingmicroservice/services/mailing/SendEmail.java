package si.fri.rso.mailingmicroservice.services.mailing;

import si.fri.rso.mailingmicroservice.lib.Attachment;
import si.fri.rso.mailingmicroservice.lib.Mail;

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

import java.util.Properties;

import java.util.logging.Logger;

@RequestScoped
public class SendEmail {
    private final Session session;

    private Logger log = Logger.getLogger(SendEmail.class.getName());

    public SendEmail() {
        // TODO: Read from config server
        Dotenv dotenv = Dotenv.load();
        Properties prop = new Properties();

        prop.put("mail.smtp.auth", false);
        prop.put("mail.smtp.starttls.enable", "true");
        prop.put("mail.smtp.host", dotenv.get("SMTP_HOST"));
        prop.put("mail.smtp.port", dotenv.get("SMTP_PORT"));
        prop.put("mail.smtp.ssl.trust", dotenv.get("SMTP_HOST"));

        this.session = Session.getInstance(prop);
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

            /* TODO: Get PDF in base64 from invoice microservice and convert it to an image and set as an attachment
            for(Attachment attachment : mail.getAttachements()) {
                MimeBodyPart mailAttachment = new MimeBodyPart();
                mailAttachment.attachFile();
            }
            */

            message.setContent(multipart);

            Transport.send(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}