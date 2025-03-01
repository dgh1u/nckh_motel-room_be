package com.nckh.motelroom.dto.entity;

import lombok.Data;

import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class PostDto {
    private long id;

    private String title;

    private String content;

    private boolean approved;

    private boolean notApproved;

    private LocalDateTime createAt;

    private LocalDateTime lastUpdate;

    private boolean del;

    private UserDto userDTO;

    private AccomodationDto accomodationDTO;

    private List<CommentDto> commentDTOS;

    private List<String> imageStrings;

    private String type;
}
