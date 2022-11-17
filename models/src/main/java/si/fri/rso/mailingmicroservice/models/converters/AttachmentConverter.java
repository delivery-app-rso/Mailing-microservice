package si.fri.rso.mailingmicroservice.models.converters;

import si.fri.rso.mailingmicroservice.lib.Attachment;
import si.fri.rso.mailingmicroservice.models.entities.AttachmentEntity;

public class AttachmentConverter {

    public static Attachment toDto(AttachmentEntity entity) {

        Attachment dto = new Attachment();
        dto.setId(entity.getId());
        dto.setTitle(entity.getTitle());
        dto.setType(entity.getType());
        dto.setCreatedAt(entity.getCreatedAt());

        return dto;

    }

    public static AttachmentEntity toEntity(Attachment dto) {
        AttachmentEntity entity = new AttachmentEntity();
        entity.setId(dto.getId());
        entity.setTitle(dto.getTitle());
        entity.setType(dto.getType());
        entity.setCreatedAt(dto.getCreatedAt());

        return entity;

    }
}
