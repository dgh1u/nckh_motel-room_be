package com.nckh.motelroom.dto.entity;

import com.nckh.motelroom.model.enums.NotificationName;
import lombok.Data;

import java.time.Instant;
import java.time.LocalDateTime;

@Data
public class NotificationDto {
    private Long id;

    private PostDto postDTO;

    private boolean seen;

    private Instant createAt;

    private NotificationName notificationName;
}
