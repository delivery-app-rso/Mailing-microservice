package si.fri.rso.mailingmicroservice.models.converters;

import si.fri.rso.mailingmicroservice.lib.Attachement;
import si.fri.rso.mailingmicroservice.models.entities.AttachmentEntity;

public class AttachmentConverter {
    public static Attachement toDto(AttachmentEntity entity) {

        Attachement dto = new Attachement();
        dto.setId(entity.getId());
        dto.setTitle(entity.getTitle());
        dto.setType(entity.getType());
        dto.setCreatedAt(entity.getCreatedAt());

        return dto;

    }

    public static AttachmentEntity toEntity(Attachement dto) {

        AttachmentEntity entity = new AttachmentEntity();
        entity.setId(dto.getId());
        entity.setMail(MailConverter.toEntity(dto.getMail()));
        entity.setTitle(dto.getTitle());
        entity.setType(dto.getType());
        entity.setCreatedAt(dto.getCreatedAt());

        return entity;

    }
}
