package si.fri.rso.mailingmicroservice.models.converters;

import si.fri.rso.mailingmicroservice.lib.Mail;
import si.fri.rso.mailingmicroservice.models.entities.MailEntity;

public class MailConverter {
    public static Mail toDto(MailEntity entity) {

        Mail dto = new Mail();
        dto.setId(entity.getId());
        dto.setSender(entity.getSender());
        dto.setRecipient(entity.getRecipient());
        dto.setBody(entity.getBody());
        dto.setSubject(entity.getSubject());

        return dto;

    }

    public static MailEntity toEntity(Mail dto) {

        MailEntity entity = new MailEntity();
        entity.setId(dto.getId());
        entity.setSender(dto.getSender());
        entity.setRecipient(dto.getRecipient());
        entity.setBody(dto.getBody());
        entity.setSubject(dto.getSubject());

        return entity;

    }
}
