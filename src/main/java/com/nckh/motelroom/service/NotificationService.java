package com.nckh.motelroom.service;

import com.nckh.motelroom.dto.entity.NotificationDto;
import com.nckh.motelroom.model.Notification;
import org.springframework.data.domain.Page;

public interface NotificationService {
    void createNotification(Notification notification);

    Page<NotificationDto> getNotificationsByEmail(String email, int page, boolean screen);

    Page<NotificationDto> getNotificationsByEmailAndCriteria(String email, Long criteria, int page);

    NotificationDto seenNotification(Long id);
}
