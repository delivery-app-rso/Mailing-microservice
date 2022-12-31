package si.fri.rso.mailingmicroservice.lib;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Mail {
    private Integer id;

    private List<Attachment> attachments = new ArrayList<>();

    private String sender;

    private String recipient;

    private String subject;

    private String body;

    private Date createdAt;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public List<Attachment> getAttachements() {
        return attachments;
    }

    public void setAttachements(List<Attachment> attachments) {
        this.attachments = attachments;
    }

    public void addAttachement(Attachment attachment) {
        if (this.attachments == null) {
            this.attachments = new ArrayList<>();
        }

        this.attachments.add(attachment);
    }
}
