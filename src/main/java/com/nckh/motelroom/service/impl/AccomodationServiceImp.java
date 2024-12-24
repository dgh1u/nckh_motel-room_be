package com.nckh.motelroom.service.impl;

import com.nckh.motelroom.repository.AccomodationRepository;
import com.nckh.motelroom.service.AccomodationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccomodationServiceImp implements AccomodationService {
    private final AccomodationRepository accomodationRepository;

}
