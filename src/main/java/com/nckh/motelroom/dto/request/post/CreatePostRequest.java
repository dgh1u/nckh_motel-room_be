package com.nckh.motelroom.dto.request.post;

import com.nckh.motelroom.dto.entity.AccomodationDto;
import com.nckh.motelroom.dto.request.accommodation.CreateAccommodationRequest;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CreatePostRequest {
    //Basic information of Post
    private String title;
    private String content;
    private CreateAccommodationRequest accomodation;
}
