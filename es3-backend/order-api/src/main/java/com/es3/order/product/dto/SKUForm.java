package com.es3.order.product.dto;

import java.math.BigDecimal;
import java.util.List;

public record SKUForm(
        List<String> optionValues,
        int quantity,
        BigDecimal additionalPrice
) {
}
