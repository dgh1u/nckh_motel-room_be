package com.nckh.motelroom.mapper;

import com.nckh.motelroom.dto.entity.AccomodationDto;
import com.nckh.motelroom.dto.request.accommodation.CreateAccommodationRequest;
import com.nckh.motelroom.model.Accomodation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {DistrictMapper.class})
public interface AccommodationMapper {
    AccomodationDto toAccomodationDto(Accomodation accomodation);

    @Mapping(target = "id", ignore = true)
    Accomodation toAccomodation(CreateAccommodationRequest accomodation);

    Accomodation toAccomodation(AccomodationDto accomodation);
}
