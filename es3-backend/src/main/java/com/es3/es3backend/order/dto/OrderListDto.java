package com.es3.es3backend.order.dto;

import com.es3.es3backend.order.domain.Order;
import lombok.Builder;

import java.util.List;

@Builder
public record OrderListDto (
    Long orderId,
    List<ListOrderStoreDto> orderStore
) {

    public static OrderListDto fromEntity(Order order) {
        return OrderListDto.builder()
                .orderId(order.getId())
                .orderStore(order.getOrderStores().stream().map(ListOrderStoreDto::fromEntity).toList())
                .build();
    }
}
