package com.nckh.motelroom.controller;

import com.nckh.motelroom.config.JwtConfig;
import com.nckh.motelroom.dto.request.payment.CreatePaymentRequest;
import com.nckh.motelroom.dto.request.payment.PaymentReceiveHookRequest;
import com.nckh.motelroom.dto.response.payment.CreatePaymentResponse;
import com.nckh.motelroom.exception.DataExistException;
import com.nckh.motelroom.service.PaymentService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.payos.PayOS;
import vn.payos.type.CheckoutResponseData;
import vn.payos.type.ItemData;
import vn.payos.type.PaymentData;

import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping("/api/payment")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;
    private final JwtConfig jwtConfig;

    @PostMapping("/create")
    public ResponseEntity<?> createPayment(@Valid @RequestBody CreatePaymentRequest request, @RequestHeader("Authorization") String token) throws Exception {
        String jwt = token.substring(7);
        Claims claims=jwtConfig.getClaims(jwt);
        System.out.println("cliams: "+claims);
        Object userIdObj = claims.get("userId");
        if (userIdObj != null) {
            Long userId = Long.parseLong(userIdObj.toString());
            // hoặc Integer.parseInt(...) nếu dùng kiểu int
            CheckoutResponseData data =paymentService.createPayment(request,userId);
            String checkoutUrl = data.getCheckoutUrl();
            return ResponseEntity.ok(CreatePaymentResponse.builder().url(checkoutUrl).build());
        }
        throw new DataExistException("Thanh toán thất bại");
    }

    //https://464d-2402-800-619d-1df1-f009-e0b5-cdc7-1b12.ngrok-free.app/api/payment/receive-hook
    @PostMapping("/receive-hook")
    public ResponseEntity<?> receiveHook(@RequestBody PaymentReceiveHookRequest request) throws Exception {
        paymentService.receiveHook(request);
        return ResponseEntity.ok("Chuyển khoản paypos");
    }
}
