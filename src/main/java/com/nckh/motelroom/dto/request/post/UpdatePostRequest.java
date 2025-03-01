package com.nckh.motelroom.dto.request.post;

import com.nckh.motelroom.dto.entity.AccomodationDto;
import lombok.Data;

@Data
public class UpdatePostRequest {
    private String title;
    private String content;
    private boolean del;
    private AccomodationDto accomodation;
}
