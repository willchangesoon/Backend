package com.es3.order.product.dto.response;

import com.es3.order.common.pagination.Identifiable;
import com.es3.order.product.domain.Product;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Builder
@Getter
public class ProductResponse implements Identifiable {
    private final Long productId;
    private final String title;
    private final String imageUrl;
    private final Long storeId;
    private final String storeName;
    private final BigDecimal price;
    private final int discount;
    private final Long categoryId;

    static public ProductResponse fromEntity(Product product) {
        return ProductResponse.builder()
                .productId(product.getId())
                .title(product.getTitle())
                .imageUrl(product.getMainImg())
                .storeId(product.getStore().getId())
                .storeName(product.getStore().getName())
                .price(product.getPrice())
                .discount(product.getDiscount())
                .categoryId(product.getCategory())
                .build();
    }

    @Override
    public Long getId() {
        return this.productId;
    }
}
