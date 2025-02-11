package com.es3.es3backend.order.controller;

import com.es3.es3backend.order.dto.OrderStoreDto;
import com.es3.es3backend.order.dto.request.OrderStoreStatusUpdateForm;
import com.es3.es3backend.order.service.SellerOrderService;
import com.es3.es3backend.seller.domain.Seller;
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
@RequestMapping("/sellers/order")
@Tag(name = "주문", description = "주문 API 입니다.")
public class SellerOrderController {
    private final SellerOrderService sellerOrderService;

    @GetMapping()
    public ResponseEntity<List<OrderStoreDto>> getOrderListBySeller(@AuthenticationPrincipal Seller seller) {
        return ResponseEntity.status(HttpStatus.OK).body(sellerOrderService.getOrderStoreList(seller));
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderStoreDto> getOrderById(@AuthenticationPrincipal Seller seller, @PathVariable long id) {
        return ResponseEntity.status(HttpStatus.OK).body(sellerOrderService.getOrderById(seller, id));
    }

    @PatchMapping("/{id}")
    public ResponseEntity updateOrderStatus(@AuthenticationPrincipal Seller seller, @PathVariable long id, @RequestBody OrderStoreStatusUpdateForm orderStoreStatusUpdateForm) {
        sellerOrderService.updateOrderStatus(seller, id, orderStoreStatusUpdateForm);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
