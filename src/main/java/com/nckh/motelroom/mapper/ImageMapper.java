package com.nckh.motelroom.mapper;

import com.nckh.motelroom.dto.entity.ImageDto;
import com.nckh.motelroom.model.Image;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
public interface ImageMapper {
    @Mapping(target = "idPost", source = "post.id")
    ImageDto toDto(Image image);
    Image toImage(ImageDto imageDto);
}
