package com.nckh.motelroom.service.impl;

import com.nckh.motelroom.exception.DataNotFoundException;
import com.nckh.motelroom.model.Accomodation;
import com.nckh.motelroom.model.Post;
import com.nckh.motelroom.repository.AccomodationRepository;
import com.nckh.motelroom.repository.custom.CustomAccomodationQuery;
import com.nckh.motelroom.repository.custom.CustomPostQuery;
import com.nckh.motelroom.service.AccomodationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccomodationServiceImp implements AccomodationService {

    private final AccomodationRepository accomodationRepository;

    @Override
    public Page<Accomodation> getAllAccomodation(CustomAccomodationQuery.AccomodationFilterParam param, PageRequest pageRequest) {
        try {
            Specification<Accomodation> specification = CustomAccomodationQuery.getFilterAccomodation(param);
            return accomodationRepository.findAll(specification, pageRequest);
        }catch (Exception e){
            throw new DataNotFoundException("Không có accomodation nào được tìm thấy! " + e.getMessage());
        }
    }
}
