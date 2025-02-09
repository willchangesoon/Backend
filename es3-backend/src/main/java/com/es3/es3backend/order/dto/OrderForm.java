package com.es3.es3backend.order.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record OrderForm(
        List<OrderItemForm> items
) {
}
