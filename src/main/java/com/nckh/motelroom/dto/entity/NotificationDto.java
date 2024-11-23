package com.nckh.motelroom.dto.entity;

import com.nckh.motelroom.model.enums.NotificationName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class NotificationDto {
    private Long id;

    private PostDto postDTO;

    private boolean seen;

    private LocalDateTime createAt;

    private NotificationName notificationName;
}
