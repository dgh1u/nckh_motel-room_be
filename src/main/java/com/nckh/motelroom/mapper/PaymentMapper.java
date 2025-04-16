package com.nckh.motelroom.mapper;

import com.nckh.motelroom.dto.entity.Payment;

import com.nckh.motelroom.model.PaymentHistory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
public interface PaymentMapper {
    Payment toPaymentDTO(PaymentHistory paymentHistory);
}