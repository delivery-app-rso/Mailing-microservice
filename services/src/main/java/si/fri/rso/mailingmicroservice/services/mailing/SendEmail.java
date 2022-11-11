package si.fri.rso.mailingmicroservice.services.mailing;

import si.fri.rso.mailingmicroservice.lib.Mail;

import javax.enterprise.context.RequestScoped;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Properties;

@RequestScoped
public class SendEmail {
    private final Session session;

    public SendEmail() {
        Properties prop = new Properties();
        prop.put("mail.smtp.auth", false);
        prop.put("mail.smtp.starttls.enable", "true");
        prop.put("mail.smtp.host", "localhost");
        prop.put("mail.smtp.port", 1025);
        prop.put("mail.smtp.ssl.trust", "localhost");

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

            message.setContent(multipart);

            Transport.send(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}