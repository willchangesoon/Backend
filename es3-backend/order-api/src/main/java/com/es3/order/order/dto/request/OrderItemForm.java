package com.es3.order.order.dto.request;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record OrderItemForm(
        Long productId,
        int quantity,
        BigDecimal unitPrice,
        Long shopId
) {
}
