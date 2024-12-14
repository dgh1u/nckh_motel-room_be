package com.nckh.motelroom.mapper;

import com.nckh.motelroom.dto.entity.ImageDto;
import com.nckh.motelroom.model.Image;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ImageMapper {
    ImageDto toDto(Image image);
    Image toImage(ImageDto imageDto);
}
