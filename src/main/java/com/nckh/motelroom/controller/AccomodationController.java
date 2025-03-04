package com.nckh.motelroom.controller;

import com.nckh.motelroom.service.AccomodationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AccomodationController {
    private final AccomodationService accomodationService;

}