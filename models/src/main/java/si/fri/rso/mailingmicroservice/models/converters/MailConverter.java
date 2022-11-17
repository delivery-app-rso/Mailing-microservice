package si.fri.rso.mailingmicroservice.models.converters;

import java.util.ArrayList;
import java.util.List;

import si.fri.rso.mailingmicroservice.lib.Mail;
import si.fri.rso.mailingmicroservice.lib.Attachment;
import si.fri.rso.mailingmicroservice.models.entities.AttachmentEntity;
import si.fri.rso.mailingmicroservice.models.entities.MailEntity;

public class MailConverter {
    public static Mail toDto(MailEntity entity) {

        Mail dto = new Mail();
        List<Attachment> attachments = new ArrayList<>();

        for (AttachmentEntity ent : entity.getAttachements()) {
            attachments.add(AttachmentConverter.toDto(ent));
        }

        dto.setId(entity.getId());
        dto.setAttachements(attachments);
        dto.setSender(entity.getSender());
        dto.setRecipient(entity.getRecipient());
        dto.setBody(entity.getBody());
        dto.setSubject(entity.getSubject());
        dto.setCreatedAt(entity.getCreatedAt());

        return dto;

    }

    public static MailEntity toEntity(Mail dto) {

        MailEntity entity = new MailEntity();
        List<AttachmentEntity> attachments = new ArrayList<>();

        for (Attachment attachment : dto.getAttachements()) {
            attachments.add(AttachmentConverter.toEntity(attachment));
        }

        entity.setId(dto.getId());
        entity.setAttachements(attachments);
        entity.setSender(dto.getSender());
        entity.setRecipient(dto.getRecipient());
        entity.setBody(dto.getBody());
        entity.setSubject(dto.getSubject());
        entity.setCreatedAt(dto.getCreatedAt());

        return entity;

    }
}
