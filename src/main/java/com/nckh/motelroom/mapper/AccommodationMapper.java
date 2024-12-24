package com.nckh.motelroom.mapper;

import com.nckh.motelroom.dto.entity.AccomodationDto;
import com.nckh.motelroom.model.Accomodation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
public interface AccommodationMapper {

    Accomodation toAccomodation(AccomodationDto accomodationDto);

    AccomodationDto toAccomodationDto(Accomodation accomodation);
}
