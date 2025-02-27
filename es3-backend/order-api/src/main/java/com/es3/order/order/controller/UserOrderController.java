package com.es3.order.order.controller;

import com.es3.order.order.dto.OrderDetailDto;
import com.es3.order.order.dto.OrderListDto;
import com.es3.order.order.service.UserOrderService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/users/order")
@Tag(name = "주문" , description = "주문 API 입니다.")
public class UserOrderController {
    private final UserOrderService userOrderService;

    @GetMapping("/{id}")
    public ResponseEntity<OrderDetailDto> getOrderDetailById(@RequestHeader("X-User-Id") String userId, @PathVariable long id) {
        return ResponseEntity.status(HttpStatus.OK).body(userOrderService.getOrderDetailByOrderId(userId, id));
    }

    @GetMapping
    public ResponseEntity<List<OrderListDto>> getOrderListByUser(@RequestHeader("X-User-Id") String userId) {
        return ResponseEntity.status(HttpStatus.OK).body(userOrderService.getOrderListByUser(userId));
    }

    @GetMapping("/{id}/delivered")
    public ResponseEntity setDelivered(@PathVariable long id) {
        userOrderService.delivered(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/{id}/complete")
    public ResponseEntity setComplete(@PathVariable long id) {
        userOrderService.complete(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
