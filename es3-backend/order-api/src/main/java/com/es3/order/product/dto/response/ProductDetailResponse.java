package com.es3.order.product.dto.response;

import com.es3.order.common.pagination.Identifiable;
import com.es3.order.product.domain.Product;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;

@Builder
@Getter
public class ProductDetailResponse implements Identifiable {
    private final Long productId;
    private final String title;
    private final String imageUrl;
    private final Long storeId;
    private final String storeName;
    private final BigDecimal price;
    private final int discount;
    private final Long categoryId;

    private final String storeLogoImg;
    private final List<String> additionalImages;
    private final String description;

    private final List<ProductOptionGroupResponse> optionGroups;
    private final List<ProductSKUResponse> skuList;

    public static ProductDetailResponse fromEntity(Product product) {
        return ProductDetailResponse.builder()
                .productId(product.getId())
                .title(product.getTitle())
                .imageUrl(product.getMainImg())
                .storeId(product.getStore().getId())
                .storeName(product.getStore().getName())
                .price(product.getPrice())
                .discount(product.getDiscount())
                .categoryId(product.getCategory())
                .storeLogoImg(product.getStore().getLogoImg())
                .additionalImages(product.getAdditionalImages())
                .description(product.getDescription())
                .optionGroups(product.getOptionGroups().stream()
                        .map(ProductOptionGroupResponse::fromEntity)
                        .toList())
                .skuList(product.getProductSKUs().stream()
                        .map(ProductSKUResponse::fromEntity)
                        .toList())
                .build();
    }

    @Override
    public Long getId() {
        return productId;
    }
}
