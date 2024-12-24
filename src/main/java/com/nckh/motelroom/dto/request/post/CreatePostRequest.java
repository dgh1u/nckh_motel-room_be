package com.nckh.motelroom.dto.request.post;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CreatePostRequest {
    //Basic information of Post
    private String title;
    private String content;
}
