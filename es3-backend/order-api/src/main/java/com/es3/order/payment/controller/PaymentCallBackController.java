package com.es3.order.payment.controller;

import com.es3.order.payment.dto.PaymentDto;
import com.es3.order.payment.service.PaymentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/payment-callback")
@Tag(name = "PG사 CallBack", description = "PG사로부터 결제 결과를 요청받는 API 입니다.")
public class PaymentCallBackController {
    private final PaymentService paymentService;

    @PostMapping("/momo-notify")
    @Operation(summary = "momo 결제 결과", description = "momo에서 결제 결과를 전달받을 api입니다.")
    public ResponseEntity<PaymentDto> handleMoMoCallback(@RequestBody Map<String, Object> payload) {
        PaymentDto paymentResult = paymentService.momoCallback(payload);
        return ResponseEntity.status(HttpStatus.OK).body(paymentResult);
    }
}
