package com.nckh.motelroom.dto.entity;

import lombok.Data;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDateTime;

/**
 * DTO for {@link com.nckh.motelroom.model.Comment}
 */
@Data
public class CommentDto {
    private long id;

    private String content;

    private LocalDateTime lastUpdate;

    private Long idPost;

    private UserDto userDTO;

    private Long rate;
}