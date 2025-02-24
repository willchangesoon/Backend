package com.es3.order.order.controller;

import com.es3.order.order.dto.OrderStoreDto;
import com.es3.order.order.service.SellerOrderService;
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
@RequestMapping("/sellers/order")
@Tag(name = "주문", description = "주문 API 입니다.")
public class SellerOrderController {
    private final SellerOrderService sellerOrderService;

    @GetMapping()
    public ResponseEntity<List<OrderStoreDto>> getOrderListBySeller(@RequestHeader("X-User-Id") String userId) {
        return ResponseEntity.status(HttpStatus.OK).body(sellerOrderService.getOrderStoreList(userId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderStoreDto> getOrderById(@RequestHeader("X-User-Id") String userId, @PathVariable long id) {
        return ResponseEntity.status(HttpStatus.OK).body(sellerOrderService.getOrderById(userId, id));
    }

    @GetMapping("/{id}/prepare-shipment")
    public ResponseEntity setPrepareShipmentStatus(@RequestHeader("X-User-Id") String userId, @PathVariable long id) {
        sellerOrderService.prepareShipment(userId, id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/{id}/shipping")
    public ResponseEntity setShipping(@RequestHeader("X-User-Id") String userId,@PathVariable long id) {
        sellerOrderService.shipping(userId, id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
