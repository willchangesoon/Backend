package com.es3.es3backend.order.dto;

public record OrderItems(
        Long productId,
        int quantity,
        Long unitPrice
) {
}
