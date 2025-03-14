package com.nckh.motelroom.dto.request.accommodation;

import com.nckh.motelroom.repository.custom.CustomAccomodationQuery;
import com.nckh.motelroom.repository.custom.CustomPostQuery;
import jakarta.validation.constraints.Min;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import java.math.BigDecimal;

@Data
public class GetAccomodationRequest extends CustomAccomodationQuery.AccomodationFilterParam {
    @Min(value = 0, message = "Số trang phải bắt đầu từ 0")
    private int start = 0;

    @Range(min = 5, max = 50, message = "Số lượng kết quả trên một trang là từ 5 đến 50")
    private int limit = 5;
}
