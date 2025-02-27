package com.es3.order.payment.controller;

import com.es3.order.payment.service.PaymentService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/payment")
@Tag(name = "결제", description = "결제 API 입니다.")
public class PaymentController {
    private final PaymentService paymentService;

}
