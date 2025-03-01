package com.nckh.motelroom.listener;

import com.nckh.motelroom.event.NotificationEvent;
import com.nckh.motelroom.model.Notification;
import com.nckh.motelroom.model.User;
import com.nckh.motelroom.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class NotificationListener implements ApplicationListener<NotificationEvent> {

    @Autowired
    private NotificationRepository notificationRepository;

    @Override
    public void onApplicationEvent(NotificationEvent event) {
        // Tạo một thông báo mới từ event
        Notification notification = new Notification();
        notification.setPost(event.getPost());
        notification.setUser(event.getUser());
        notification.setSeen(false);
        notification.setCreateAt(java.time.Instant.now());
        notification.setNotificationName(com.nckh.motelroom.model.enums.NotificationName.valueOf(event.getMessage()));

        // Lưu thông báo vào cơ sở dữ liệu
        notificationRepository.save(notification);
    }
}
