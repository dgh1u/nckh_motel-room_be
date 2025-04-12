package com.nckh.motelroom.service.impl;

import com.nckh.motelroom.dto.entity.Payment;
import com.nckh.motelroom.dto.request.payment.CreatePaymentRequest;
import com.nckh.motelroom.dto.request.payment.PaymentReceiveHookRequest;
import com.nckh.motelroom.exception.DataExistException;
import com.nckh.motelroom.model.PaymentHistory;
import com.nckh.motelroom.model.User;
import com.nckh.motelroom.repository.PaymentRepository;
import com.nckh.motelroom.repository.UserRepository;
import com.nckh.motelroom.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import vn.payos.PayOS;
import vn.payos.type.CheckoutResponseData;
import vn.payos.type.ItemData;
import vn.payos.type.PaymentData;
import vn.payos.type.WebhookData;

import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    @Value("${FRONTEND_URL}")
    private String frontendUrl;

    private final PayOS payOS;
    private final UserRepository userRepository;
    private final PaymentRepository paymentRepository;
    @Override
    public CheckoutResponseData createPayment(CreatePaymentRequest request,Long id) {
        try{
            String currentTimeString = String.valueOf(new Date().getTime());
            long orderCode = Long.parseLong(currentTimeString.substring(currentTimeString.length() - 6));
            ItemData item = ItemData.builder().name(id+" "+request.getDesc()).quantity(1).price(request.getPrice()).build();
            PaymentData paymentData = PaymentData.builder().orderCode(orderCode).amount(request.getPrice()).description(id+" "+request.getDesc())
                    .returnUrl(frontendUrl).cancelUrl(frontendUrl).item(item).build();
            return payOS.createPaymentLink(paymentData);
        }catch (Exception e){
            throw new DataExistException("Thanh toán thất bại: " + e.getMessage());
        }
    }

    @Override
    public void receiveHook(PaymentReceiveHookRequest request) {
        String[] parts = request.getData().getDescription().split(" ");
        System.out.println("parts: "+parts[1]);
        Long id= Long.parseLong(parts[1]);
        Optional<User> userOptional=userRepository.findById(id);
        if(!userOptional.isPresent()){
            throw new DataExistException("Không tồn tại người dùng");
        }
        User user=userOptional.get();
        Integer price=user.getBalance()+request.getData().getAmount();
        user.setBalance(price);
        try {
            userRepository.save(user);
            PaymentHistory payment=new PaymentHistory();
            payment.setCode(request.getCode());
            payment.setDescrip(request.getDesc());
            payment.setSuccess(request.isSuccess());
            payment.setDescStatus(request.getData().getDesc());
            payment.setOrderCode(request.getData().getOrderCode());
            payment.setAmount(request.getData().getAmount());
            payment.setDescription(request.getData().getDescription());
            payment.setAccountNumber(request.getData().getAccountNumber());
            payment.setReference(request.getData().getReference());
            payment.setTransactionDateTime(request.getData().getTransactionDateTime());
            payment.setCurrency(request.getData().getCurrency());
            payment.setPaymentLinkId(request.getData().getPaymentLinkId());
            payment.setCounterAccountBankId(request.getData().getCounterAccountBankId());
            payment.setCounterAccountBankName(request.getData().getCounterAccountBankName());
            payment.setCounterAccountName(request.getData().getCounterAccountName());
            payment.setCounterAccountNumber(request.getData().getCounterAccountNumber());
            payment.setVirtualAccountName(request.getData().getVirtualAccountName());
            payment.setVirtualAccountNumber(request.getData().getVirtualAccountNumber());
            payment.setSignature(request.getSignature());

            paymentRepository.save(payment);
        }catch (Exception e){
            throw new DataExistException("Thanh toán thất bại: " + e.getMessage());
        }
    }
}
