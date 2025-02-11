package com.es3.es3backend.order.controller;

import com.es3.es3backend.order.dto.OrderForm;
import com.es3.es3backend.order.service.OrderService;
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
@RequestMapping("/order")
@Tag(name = "주문" , description = "주문 API 입니다.")
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    @Operation(summary = "주문 생성하기", description = "유저가 주문을 생성하는 api 입니다.")
    public ResponseEntity<Long> createOrder(@AuthenticationPrincipal User user, @RequestBody OrderForm orderForm) {
        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.creatOrder(user, orderForm));
    }
}
