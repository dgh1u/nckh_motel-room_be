package com.nckh.motelroom.mapper;

import com.nckh.motelroom.dto.entity.NotificationDto;
import com.nckh.motelroom.model.Notification;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
@Component
public interface NotificationMapper {
    NotificationDto toNotificationDto(Notification notification);
    Notification toNotification(NotificationDto notificationDto);
}
