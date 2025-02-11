package com.es3.es3backend.payment.controller;

import com.es3.es3backend.payment.dto.request.PaymentForm;
import com.es3.es3backend.payment.service.PaymentService;
import com.es3.es3backend.user.domain.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/payment")
@Tag(name = "결제", description = "결제 API 입니다.")
public class PaymentController {
    private final PaymentService paymentService;

    @PostMapping("")
    @Operation(summary = "결제 내역 생성", description = "결제 내역을 생성합니다.")
    public ResponseEntity<String> createPayment(@AuthenticationPrincipal User user, @RequestBody PaymentForm paymentForm) {
        return ResponseEntity.status(HttpStatus.OK).body(paymentService.createPayment(user, paymentForm));
    }
}
