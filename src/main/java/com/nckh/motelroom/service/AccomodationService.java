package com.nckh.motelroom.service;

import com.nckh.motelroom.model.Accomodation;
import com.nckh.motelroom.model.Post;
import com.nckh.motelroom.repository.custom.CustomAccomodationQuery;
import com.nckh.motelroom.repository.custom.CustomPostQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public interface AccomodationService {
    Page<Accomodation> getAllAccomodation(CustomAccomodationQuery.AccomodationFilterParam param, PageRequest pageRequest);
}
