package com.es3.order.product.dto;

import java.math.BigDecimal;

public record ProductOptionForm(
        String name,
        String value,
        int quantity,
        BigDecimal additionalPrice
) {
}
