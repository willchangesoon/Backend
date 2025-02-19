package com.es3.es3backend.order.controller;

import com.es3.es3backend.order.dto.OrderDetailDto;
import com.es3.es3backend.order.dto.OrderListDto;
import com.es3.es3backend.order.service.UserOrderService;
import com.es3.es3backend.user.domain.User;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/users/order")
@Tag(name = "주문" , description = "주문 API 입니다.")
public class UserOrderController {
    private final UserOrderService userOrderService;

    @GetMapping("/{id}")
    public ResponseEntity<OrderDetailDto> getOrderDetailById(@AuthenticationPrincipal User user, @PathVariable long id) {
        return ResponseEntity.status(HttpStatus.OK).body(userOrderService.getOrderDetailByOrderId(user, id));
    }

    @GetMapping
    public ResponseEntity<List<OrderListDto>> getOrderListByUser(@AuthenticationPrincipal User user) {
        return ResponseEntity.status(HttpStatus.OK).body(userOrderService.getOrderListByUser(user));
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
