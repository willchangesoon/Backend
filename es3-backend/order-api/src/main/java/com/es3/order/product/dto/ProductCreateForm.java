package com.es3.order.product.dto;

import java.math.BigDecimal;
import java.util.List;

public record ProductCreateForm(
        Long storeId,
        String title,
        BigDecimal price,
        Long categoryId,
        String description,
        String mainImage,
        List<String> additionalImages,
        List<ProductOptionForm> productOptionList
) {
}
