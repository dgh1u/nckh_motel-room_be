package com.nckh.motelroom.dto.request.payment;

import lombok.*;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DataReceiveHook {
    private String id;
    private Long orderCode;
    private Long amount;
    private Long amountPaid;
    private Long amountRemaining;
    private String status;
    private String cancellationReason;
    private Instant createdAt;
    private Instant canceledAt;
}
