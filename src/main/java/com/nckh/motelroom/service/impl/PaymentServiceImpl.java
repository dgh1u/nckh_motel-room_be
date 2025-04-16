package com.nckh.motelroom.service.impl;

import com.nckh.motelroom.dto.request.payment.CreatePaymentRequest;
import com.nckh.motelroom.dto.request.payment.PaymentReceiveHookRequest;
import com.nckh.motelroom.exception.DataExistException;
import com.nckh.motelroom.model.PaymentHistory;
import com.nckh.motelroom.model.User;
import com.nckh.motelroom.repository.PaymentRepository;
import com.nckh.motelroom.repository.UserRepository;
import com.nckh.motelroom.repository.custom.CustomPaymentQuery;
import com.nckh.motelroom.service.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import vn.payos.PayOS;
import vn.payos.type.CheckoutResponseData;
import vn.payos.type.ItemData;
import vn.payos.type.PaymentData;

import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentServiceImpl implements PaymentService {

    @Value("${FRONTEND_URL}")
    private String frontendUrl;

    private final PayOS payOS;
    private final UserRepository userRepository;
    private final PaymentRepository paymentRepository;

    @Override
    public CheckoutResponseData createPayment(CreatePaymentRequest request, Long id) {
        try {
            log.info("Tạo thanh toán cho userId: {}", id);
            String currentTimeString = String.valueOf(new Date().getTime());
            Long orderCode = Long.parseLong(currentTimeString.substring(currentTimeString.length() - 6));

            // Tạo và lưu PaymentHistory (trạng thái pending) để lấy paymentHistoryId
            PaymentHistory paymentHistory = new PaymentHistory();
            paymentHistory.setOrderCode(orderCode);
            paymentHistory.setAmount(request.getPrice());
            paymentHistory.setDescription(id + " " + request.getDesc());
            paymentHistory.setSuccess(false);
            paymentHistory = paymentRepository.save(paymentHistory);

            // Xây dựng URL chuyển hướng theo mẫu: FRONTEND_URL/payment/{paymentHistoryId}/result
            String resultUrl = frontendUrl + "/" + paymentHistory.getId() + "/result";

            ItemData item = ItemData.builder()
                    .name(id + " " + request.getDesc())
                    .quantity(1)
                    .price(request.getPrice())
                    .build();
            PaymentData paymentData = PaymentData.builder()
                    .orderCode(orderCode)
                    .amount(request.getPrice())
                    .description(id + " " + request.getDesc())
                    .returnUrl(resultUrl)
                    .cancelUrl(resultUrl)
                    .item(item)
                    .build();

            CheckoutResponseData data = payOS.createPaymentLink(paymentData);

            // Cập nhật PaymentHistory với paymentLinkId trả về từ PayOS
            paymentHistory.setPaymentLinkId(data.getPaymentLinkId());
            paymentRepository.save(paymentHistory);

            return data;
        } catch (Exception e) {
            log.error("Lỗi khi tạo thanh toán: {}", e.getMessage(), e);
            throw new DataExistException("Thanh toán thất bại: " + e.getMessage());
        }
    }

    @Override
    public void receiveHook(PaymentReceiveHookRequest request) {
        try {
            String description = request.getData().getDescription();
            String[] parts = description.split(" ");
            if (parts.length < 2) {
                throw new DataExistException("Định dạng mô tả không hợp lệ, không thể lấy được user id");
            }
            Long id = Long.parseLong(parts[1]);

            Optional<User> userOptional = userRepository.findById(id);
            if (!userOptional.isPresent()) {
                throw new DataExistException("Không tồn tại người dùng");
            }
            User user = userOptional.get();
            if (request.isSuccess()) {
                Integer price = user.getBalance() + request.getData().getAmount();
                user.setBalance(price);
                userRepository.save(user);
            }

            // Tìm PaymentHistory dựa trên orderCode (kiểu Long)
            Long orderCode = request.getData().getOrderCode();
            Optional<PaymentHistory> phOpt = paymentRepository.findByOrderCode(orderCode);
            if (!phOpt.isPresent()) {
                throw new DataExistException("Không tìm thấy giao dịch tạm với orderCode: " + orderCode);
            }
            PaymentHistory payment = phOpt.get();

            // Cập nhật các thông tin từ dữ liệu PayOS
            payment.setCode(request.getCode());
            payment.setDescrip(request.getDesc());
            payment.setSuccess(request.isSuccess());
            payment.setDescStatus(request.getData().getDesc());
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
            payment.setUserId(id);

            paymentRepository.save(payment);
        } catch (Exception e) {
            log.error("Lỗi khi xử lý webhook: {}", e.getMessage(), e);
            throw new DataExistException("Thanh toán thất bại: " + e.getMessage());
        }
    }

    @Override
    public Page<PaymentHistory> getAllPayment(CustomPaymentQuery.PaymentFilterParam param, PageRequest pageRequest) {
        Specification<PaymentHistory> specification = CustomPaymentQuery.getFilterPayment(param);
        return paymentRepository.findAll(specification, pageRequest);
    }
}
