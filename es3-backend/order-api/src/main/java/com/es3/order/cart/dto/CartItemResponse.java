package com.es3.order.cart.dto;

import com.es3.order.cart.domain.CartItem;
import com.es3.order.product.domain.Product;
import com.es3.order.product.domain.ProductOption;
import com.es3.order.product.domain.ProductSKU;
import lombok.Builder;

import java.math.BigDecimal;
import java.util.stream.Collectors;

@Builder
public record CartItemResponse(
        Long cartItemId,
        Long skuId,
        Long productId,
        String productTitle,
        String optionSummary,
        int quantity,
        BigDecimal price,
        BigDecimal additionalPrice,
        String imageUrl
) {
    public static CartItemResponse fromEntity(CartItem item) {
        ProductSKU sku = item.getSku();
        Product product = sku.getProduct();

        return CartItemResponse.builder()
                .cartItemId(item.getId())
                .skuId(sku.getId())
                .productId(product.getId())
                .productTitle(product.getTitle())
                .optionSummary(sku.getOptions().stream()
                        .map(ProductOption::getValue)
                        .collect(Collectors.joining(" / ")))
                .quantity(item.getQuantity())
                .price(product.getPrice())
                .additionalPrice(sku.getAdditionalPrice())
                .imageUrl(product.getMainImg())
                .build();
    }
}

