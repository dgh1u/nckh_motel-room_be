package com.nckh.motelroom.dto.request.payment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.payos.type.ItemData;

@Data
@Builder
public class PaymentItemData {
    private String name;
    private Long quantity;
    private Integer price;
    private String userId;
}
