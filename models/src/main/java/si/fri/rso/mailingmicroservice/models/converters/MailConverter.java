package si.fri.rso.mailingmicroservice.models.converters;

import java.util.ArrayList;
import java.util.List;

import si.fri.rso.mailingmicroservice.lib.Mail;
import si.fri.rso.mailingmicroservice.lib.Attachement;
import si.fri.rso.mailingmicroservice.models.entities.AttachmentEntity;
import si.fri.rso.mailingmicroservice.models.entities.MailEntity;

public class MailConverter {
    public static Mail toDto(MailEntity entity) {

        Mail dto = new Mail();
        List<Attachement> attachements = new ArrayList<Attachement>();

        for (AttachmentEntity ent : entity.getAttachements()) {
            attachements.add(AttachmentConverter.toDto(ent));
        }

        System.out.println(attachements.size());

        dto.setId(entity.getId());
        dto.setAttachements(attachements);
        dto.setSender(entity.getSender());
        dto.setRecipient(entity.getRecipient());
        dto.setBody(entity.getBody());
        dto.setSubject(entity.getSubject());
        dto.setCreatedAt(entity.getCreatedAt());

        return dto;

    }

    public static MailEntity toEntity(Mail dto) {

        MailEntity entity = new MailEntity();
        List<AttachmentEntity> attachements = new ArrayList<AttachmentEntity>();

        for (Attachement attachement : dto.getAttachements()) {
            attachements.add(AttachmentConverter.toEntity(attachement));
        }

        entity.setId(dto.getId());
        entity.setAttachements(attachements);
        entity.setSender(dto.getSender());
        entity.setRecipient(dto.getRecipient());
        entity.setBody(dto.getBody());
        entity.setSubject(dto.getSubject());
        entity.setCreatedAt(dto.getCreatedAt());

        return entity;

    }
}
