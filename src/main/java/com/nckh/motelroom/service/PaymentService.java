package com.nckh.motelroom.service;

import com.nckh.motelroom.dto.request.payment.CreatePaymentRequest;
import com.nckh.motelroom.dto.request.payment.PaymentReceiveHookRequest;
import vn.payos.type.CheckoutResponseData;
import vn.payos.type.PaymentData;

public interface PaymentService {
    CheckoutResponseData createPayment(CreatePaymentRequest request,Long id);

    void receiveHook(PaymentReceiveHookRequest request);
}
