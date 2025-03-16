package com.nckh.motelroom.mapper;

import com.nckh.motelroom.dto.entity.DistrictDto;
import com.nckh.motelroom.model.District;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DistrictMapper {

    // Ánh xạ từ idDistrict thành District
    District toDistrict(Long idDistrict);

    DistrictDto toDistrictDto(District district);
}
