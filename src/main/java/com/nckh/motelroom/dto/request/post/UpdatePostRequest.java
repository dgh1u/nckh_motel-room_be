package com.nckh.motelroom.dto.request.post;

import com.nckh.motelroom.dto.entity.AccomodationDto;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UpdatePostRequest {
    @NotNull
    @Size(min = 10, max = 100)
    private String title;
    @NotNull
    @Size(min = 50, max = 500)
    private String content;
    private AccomodationDto accomodation;
}
