package si.fri.rso.mailingmicroservice.models.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.*;

@Entity
@Table(name = "mails")
@NamedQueries(value = {
        @NamedQuery(name = "MailEntity.getAll", query = "SELECT im FROM MailEntity im")
})
public class MailEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToMany(mappedBy = "mail")
    private List<AttachmentEntity> attachements;

    @Column(name = "sender")
    private String sender;

    @Column(name = "recipient")
    private String recipient;

    @Column(name = "subject")
    private String subject;

    @Column(name = "body")
    private String body;

    @Column(name = "createdAt", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
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

    public List<AttachmentEntity> getAttachements() {
        return attachements;
    }

    public void setAttachements(List<AttachmentEntity> attachements) {
        this.attachements = attachements;
    }

    public void addAttachement(AttachmentEntity attachement) {
        if(this.attachements == null) {
            this.attachements = new ArrayList<>();
        }

        this.attachements.add(attachement);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}