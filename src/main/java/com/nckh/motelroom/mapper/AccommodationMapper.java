package com.nckh.motelroom.mapper;

import com.nckh.motelroom.dto.entity.AccomodationDto;
import com.nckh.motelroom.dto.request.accommodation.CreateAccommodationRequest;
import com.nckh.motelroom.model.Accomodation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring", uses = {DistrictMapper.class})
public interface AccommodationMapper {
    @Mapping(source = "idDistrict", target = "district")
    Accomodation toAccomodation(AccomodationDto accomodationDto);

    @Mapping(source = "district.id", target = "idDistrict")
    AccomodationDto toAccomodationDto(Accomodation accomodation);

    @Mapping(target = "id", ignore = true)
    Accomodation toAccomodation(CreateAccommodationRequest accomodation);
}
