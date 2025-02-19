package com.es3.es3backend.payment.service;

import com.es3.es3backend.config.exception.AuthException;
import com.es3.es3backend.config.exception.ErrorCode;
import com.es3.es3backend.config.exception.PaymentException;
import com.es3.es3backend.order.service.OrderService;
import com.es3.es3backend.payment.domain.Payment;
import com.es3.es3backend.payment.domain.PaymentRepository;
import com.es3.es3backend.payment.dto.PaymentDto;
import com.es3.es3backend.user.domain.User;
import com.es3.es3backend.user.domain.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final OrderService orderService;
    private final UserRepository  userRepository;



    private String requestMoMoPayment(Payment payment) {
//        String orderId = payment.getOrderId();
//        String requestId = String.valueOf(System.currentTimeMillis());
//
//        String rawSignature = "partnerCode=" + partnerCode +
//                "&accessKey=" + accessKey +
//                "&requestId=" + requestId +
//                "&amount=" + payment.getAmount() +
//                "&orderId=" + orderId +
//                "&returnUrl=" + returnUrl +
//                "&notifyUrl=" + notifyUrl;
//
//        String signature = hmacSHA256(rawSignature, secretKey);
//
//        Map<String, String> payload = new HashMap<>();
//        payload.put("partnerCode", partnerCode);
//        payload.put("accessKey", accessKey);
//        payload.put("requestId", requestId);
//        payload.put("amount", String.valueOf(payment.getAmount()));
//        payload.put("orderId", orderId);
//        payload.put("returnUrl", returnUrl);
//        payload.put("notifyUrl", notifyUrl);
//        payload.put("requestType", "captureMoMoWallet");
//        payload.put("signature", signature);
//
//        RestTemplate restTemplate = new RestTemplate();
//        ResponseEntity<Map> response = restTemplate.postForEntity(momoApiUrl, payload, Map.class);
//
//        return response.getBody().get("payUrl").toString();
        return "some/url/com";
    }

    public PaymentDto momoCallback(Map<String, Object> payload) {
        String orderId = (String) payload.get("orderId");
        String resultCode = String.valueOf(payload.get("resultCode"));
        Payment payment = paymentRepository.findByOrderId(Long.valueOf(orderId))
                .orElseThrow(() -> new PaymentException(ErrorCode.PAYMENT_NOT_FOUND));

        orderService.completePayment(Long.valueOf(orderId), "0".equals(resultCode));

        return PaymentDto.fromEntity(payment, "payment result message");
    }



    private User getUser(User user) {
        return userRepository.findById(user.getId()).orElseThrow(() -> new AuthException(ErrorCode.USER_NOT_FOUND));
    }
}
