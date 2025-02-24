package com.es3.order.payment.service;

import com.es3.order.config.exception.ErrorCode;
import com.es3.order.config.exception.PaymentException;
import com.es3.order.order.service.OrderService;
import com.es3.order.payment.domain.Payment;
import com.es3.order.payment.domain.PaymentRepository;
import com.es3.order.payment.dto.PaymentDto;
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
}
