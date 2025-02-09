package com.es3.es3backend.order.controller;

import com.es3.es3backend.order.dto.OrderForm;
import com.es3.es3backend.order.service.OrderService;
import com.es3.es3backend.user.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity createOrder(@AuthenticationPrincipal User user, @RequestBody OrderForm orderForm) {
        orderService.creatOrder(user, orderForm);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
