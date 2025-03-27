package com.es3.order.product.dto;

import java.util.List;

public record ProductOptionGroupForm(
        String name,
        List<String> values
) {}
