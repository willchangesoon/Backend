package com.es3.order.order.dto.request;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record OrderItemForm(
//        Long productId,
//        Long productOptionId,
//        int quantity,
//        BigDecimal unitPrice,
//        Long shopId
        Long shopId,
        Long skuId,
        int quantity,
        BigDecimal unitPrice
) {
}
