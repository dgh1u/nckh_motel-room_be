package com.nckh.motelroom.dto.request;

import com.nckh.motelroom.repository.custom.CustomUserQuery;
import jakarta.validation.constraints.Min;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

@Data
public class GetUserRequest extends CustomUserQuery.UserFilterParam {
    @Min(value = 0, message = "Số trang phải bắt đầu từ 0")
    private int start = 0;
    @Range(min = 5, max = 50, message = "Số lượng người dùng trong một trang là từ 5 đến 50 người")
    private int limit = 10;
}
