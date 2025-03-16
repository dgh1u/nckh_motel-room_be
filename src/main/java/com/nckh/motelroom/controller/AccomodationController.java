package com.nckh.motelroom.controller;

import com.nckh.motelroom.dto.request.accommodation.GetAccomodationRequest;
import com.nckh.motelroom.dto.request.post.GetPostRequest;
import com.nckh.motelroom.dto.response.BaseResponse;
import com.nckh.motelroom.mapper.AccommodationMapper;
import com.nckh.motelroom.model.Accomodation;
import com.nckh.motelroom.model.Post;
import com.nckh.motelroom.service.AccomodationService;
import io.swagger.annotations.ApiOperation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AccomodationController {
    private final AccomodationService accomodationService;
    private final AccommodationMapper accommodationMapper;

    // hoàn thành
    @ApiOperation(value = "Lấy tất cả")
    @GetMapping("/accomodations")
    public ResponseEntity<?> getAllAccomodation(@Valid @ModelAttribute GetAccomodationRequest request) {
        Page<Accomodation> page = accomodationService.getAllAccomodation(request, PageRequest.of(request.getStart(), request.getLimit()));
        return BaseResponse.successListData(page.getContent().stream().map(accommodationMapper::toAccomodationDto).collect(Collectors.toList()), (int) page.getTotalElements());
    }

}