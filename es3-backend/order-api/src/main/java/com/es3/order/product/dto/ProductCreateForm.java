package com.es3.order.product.dto;

import java.math.BigDecimal;
import java.util.List;

public record ProductCreateForm(
        String title,
        BigDecimal price,
        Long categoryId,
        boolean visibility,
        String deliveryType,
        String description,
        String mainImage,
        List<String> additionalImages,
        List<ProductOptionForm> productOptionList
) {
}
