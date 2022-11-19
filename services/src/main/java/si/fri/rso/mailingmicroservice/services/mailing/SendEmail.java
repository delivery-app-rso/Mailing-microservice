package si.fri.rso.mailingmicroservice.services.mailing;

import si.fri.rso.mailingmicroservice.lib.Attachment;
import si.fri.rso.mailingmicroservice.lib.Mail;
import si.fri.rso.mailingmicroservice.services.config.MailingProperties;
import si.fri.rso.mailingmicroservice.services.minio.MinioHandler;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.annotation.PostConstruct;
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
import javax.mail.util.ByteArrayDataSource;

import io.minio.MinioClient;

import java.io.InputStream;
import java.util.Properties;

import java.util.logging.Logger;

@RequestScoped
public class SendEmail {

    @Inject
    private MinioHandler minioHandler;

    @Inject
    MailingProperties mailingProperties;

    private Session session;

    private Logger log = Logger.getLogger(SendEmail.class.getName());

    @PostConstruct
    public void initMailConfigs() {
        Properties prop = new Properties();
        prop.put("mail.smtp.auth", false);
        prop.put("mail.smtp.starttls.enable", "true");
        prop.put("mail.smtp.host", this.mailingProperties.getHost());
        prop.put("mail.smtp.port", this.mailingProperties.getPort());
        prop.put("mail.smtp.ssl.trust", this.mailingProperties.getSenderEmail());

        this.session = Session.getInstance(prop);
    }

    private DataSource getInvoiceDataSource(String fileName) {
        InputStream inputStream = this.minioHandler.getInvoiceFromStorage(fileName);

        ByteArrayDataSource ds = null;

        try {
            ds = new ByteArrayDataSource(inputStream, "application/pdf");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ds;
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

            for (Attachment attachment : mail.getAttachements()) {
                MimeBodyPart mailAttachment = new MimeBodyPart();
                String attachmentFile = attachment.getTitle() + ".pdf";

                mailAttachment.setDataHandler(new DataHandler(this.getInvoiceDataSource(attachmentFile)));
                mailAttachment.setFileName(attachmentFile);
                multipart.addBodyPart(mailAttachment);
            }

            message.setContent(multipart);

            Transport.send(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}