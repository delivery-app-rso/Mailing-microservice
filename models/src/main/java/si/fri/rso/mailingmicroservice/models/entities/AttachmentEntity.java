package si.fri.rso.mailingmicroservice.models.entities;

import java.util.Date;
import javax.persistence.*;

@Entity
@Table(name = "attachments")
@NamedQueries(value = {
        @NamedQuery(name = "AttachmentEntity.getAll", query = "SELECT im FROM AttachmentEntity im")
})
public class AttachmentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne()
    @JoinColumn(name = "mail_id")
    private MailEntity mail;

    @Column(name = "title")
    private String title;

    @Column(name = "type")
    private String type;

    @Column(name = "createdAt", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date createdAt;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public MailEntity getMail() {
        return mail;
    }

    public void setMail(MailEntity mail) {
        this.mail = mail;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
