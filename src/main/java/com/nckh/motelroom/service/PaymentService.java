package com.nckh.motelroom.service;

import com.nckh.motelroom.dto.request.payment.CreatePaymentRequest;
import com.nckh.motelroom.dto.request.payment.PaymentReceiveHookRequest;
import com.nckh.motelroom.model.PaymentHistory;
import com.nckh.motelroom.repository.custom.CustomPaymentQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import vn.payos.type.CheckoutResponseData;
import vn.payos.type.PaymentData;

public interface PaymentService {
    CheckoutResponseData createPayment(CreatePaymentRequest request,Long id);

    void receiveHook(PaymentReceiveHookRequest request);

    Page<PaymentHistory> getAllPayment(CustomPaymentQuery.PaymentFilterParam param, PageRequest pageRequest);

}
