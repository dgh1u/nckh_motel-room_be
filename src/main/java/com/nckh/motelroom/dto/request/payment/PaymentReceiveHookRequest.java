package com.nckh.motelroom.dto.request.payment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.payos.type.WebhookData;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentReceiveHookRequest {
    private int code;
    private String desc;
    private boolean success;
    private WebhookData data;
    private String signature;
}
