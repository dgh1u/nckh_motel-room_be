package com.nckh.motelroom.dto.response.post;

import com.nckh.motelroom.dto.entity.AccomodationDto;
import com.nckh.motelroom.dto.entity.UserDto;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UpdatePostResponse {
    private long id;
    private String title;
    private String content;
    private boolean approved;
    private boolean notApproved;
    private LocalDateTime createAt;
    private LocalDateTime lastUpdate;
    private boolean del;
    private AccomodationDto accomodationDTO;
    private UserDto userDTO;
}

