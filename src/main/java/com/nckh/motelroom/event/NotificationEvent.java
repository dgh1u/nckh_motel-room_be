package com.nckh.motelroom.event;

import com.nckh.motelroom.model.Post;
import com.nckh.motelroom.model.User;
import lombok.*;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
public class NotificationEvent extends ApplicationEvent {
    private Post post;
    private User user;
    private String message;

    // Constructor chấp nhận tất cả các tham số
    public NotificationEvent(Object source, Post post, User user, String message) {
        super(source);  // Đây là tham số bắt buộc đối với constructor của ApplicationEvent
        this.post = post;
        this.user = user;
        this.message = message;
    }
}
