package com.nckh.motelroom.dto.request.post;

import com.nckh.motelroom.dto.entity.AccomodationDto;
import com.nckh.motelroom.dto.request.accommodation.CreateAccommodationRequest;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CreatePostRequest {
    //Basic information of Post
    @NotNull
    @Size(min = 3, max = 100)
    private String title;
    @NotNull
    @Size(min = 10, max = 500)
    private String content;
    private CreateAccommodationRequest accomodation;
}
