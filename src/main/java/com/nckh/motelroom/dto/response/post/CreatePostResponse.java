package com.nckh.motelroom.dto.response.post;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CreatePostResponse {
    private Long id;
    private String title;
    private String content;
    private LocalDateTime createAt;
    private LocalDateTime lastUpdate;
    private String user;
    private Long accomodationId;
    private String type;
}
