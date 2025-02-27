package com.es3.order.order.dto;

import com.es3.order.order.domain.Order;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record OrderDetailDto(
        Long orderId,
        PaymentInfoDto paymentInfo,
        List<OrderStoreDto> orderStore,
        LocalDateTime createdAt
) {

    public static OrderDetailDto fromEntity(Order order) {
        return OrderDetailDto.builder()
                .orderId(order.getId())
                .paymentInfo(PaymentInfoDto.fromEntity(order.getPayment()))
                .orderStore(order.getOrderStores().stream().map(OrderStoreDto::fromEntity).toList())
                .createdAt(order.getCreatedDate())
                .build();
    }
}
