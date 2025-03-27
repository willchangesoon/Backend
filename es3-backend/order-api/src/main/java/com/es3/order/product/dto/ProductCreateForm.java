package com.es3.order.product.dto;

import java.math.BigDecimal;
import java.util.List;

public record ProductCreateForm(
        String title,
        BigDecimal price,
        boolean visibility,
        String deliveryType,
        Long categoryId,
        String mainImage,
        List<String> additionalImages,
        String description,

        List<ProductOptionGroupForm> optionGroups,
        List<SKUForm> skus
) {
}
