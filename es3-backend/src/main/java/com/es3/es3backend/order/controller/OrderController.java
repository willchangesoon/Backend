package com.es3.es3backend.order.controller;

import com.es3.es3backend.order.dto.OrderDetailDto;
import com.es3.es3backend.order.dto.request.OrderForm;
import com.es3.es3backend.order.service.OrderService;
import com.es3.es3backend.user.domain.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/order")
@Tag(name = "주문", description = "주문 API 입니다.")
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    @Operation(summary = "주문 생성하기", description = "유저가 주문을 생성하는 api 입니다.")
    public ResponseEntity<Long> createOrder(@AuthenticationPrincipal User user, @RequestBody OrderForm orderForm) {
        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.creatOrder(user, orderForm.storeIds(),
                orderForm.items(), orderForm.paymentMethod()));
    }

    @PostMapping("/{orderId}/payment")
    @Operation(summary = "주문 결제 완료", description = "주문에 대한 결제가 완료됐을떄 상태를 변경하는 api 입니다.")
    public ResponseEntity<OrderDetailDto> completePayment(@PathVariable Long orderId) {
        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.completePayment(orderId, true));
    }


    @PostMapping("/{orderId}/cancel")
    @Operation(summary = "주문 취소하기", description = "유저가 주문을 취소하는 api 입니다.")
    public ResponseEntity<OrderDetailDto> cancelPayment(@PathVariable Long orderId, @RequestBody List<Long> orderItemIds) {
        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.cancelOrder(orderId, orderItemIds));
    }
}
