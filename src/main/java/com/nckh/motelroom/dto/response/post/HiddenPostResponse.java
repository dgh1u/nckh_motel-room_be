package com.nckh.motelroom.dto.response.post;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HiddenPostResponse {
    private Long postId;       // ID của bài đăng
    private String message;    // Nội dung thông báo
    private boolean hidden;
}
