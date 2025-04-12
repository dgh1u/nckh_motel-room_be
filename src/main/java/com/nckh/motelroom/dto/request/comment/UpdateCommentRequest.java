package com.nckh.motelroom.dto.request.comment;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateCommentRequest {
    @NotNull(message = "Id không được để trống")
    private Long id;

    @NotBlank(message = "Nội dung không được để trống")
    private String content;

    @Min(value = 0, message = "Rate phải lớn hơn hoặc bằng 0")
    private long rate;
}
