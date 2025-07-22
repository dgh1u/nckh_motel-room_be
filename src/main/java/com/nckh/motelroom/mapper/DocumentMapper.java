// src/main/java/com/nckh/motelroom/mapper/DocumentMapper.java
package com.nckh.motelroom.mapper;

import com.nckh.motelroom.dto.entity.DocumentDto;
import com.nckh.motelroom.model.Document;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DocumentMapper {
    @Mapping(target = "idPost", source = "post.id")
    DocumentDto toDto(Document document);
}