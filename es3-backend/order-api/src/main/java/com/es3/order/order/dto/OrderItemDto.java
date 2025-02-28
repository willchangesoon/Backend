package com.es3.order.order.dto;

import com.es3.order.order.domain.OrderItem;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record OrderItemDto(
        Long id,
        BigDecimal unitPrice,
        int quantity,
        ProductDto productDto
) {

    @Builder
    public record ProductDto(
            Long id,
            String title,
            String imgUrl
    ) {

    }

    public static OrderItemDto fromEntity(OrderItem orderItem) {
        return OrderItemDto.builder()
                .id(orderItem.getId())
                .unitPrice(orderItem.getUnitPrice())
                .quantity(orderItem.getQuantity())
                .build();
    }
}
