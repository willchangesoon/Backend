package com.es3.order.product.dto.response;

import com.es3.order.product.domain.ProductOption;
import com.es3.order.product.domain.ProductSKU;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;

@Builder
@Getter
public class ProductSKUResponse {
    private Long skuId;
    private List<String> optionValueList;
    private int quantity;
    private BigDecimal additionalPrice;

    public static ProductSKUResponse fromEntity(ProductSKU sku) {
        return ProductSKUResponse.builder()
                .skuId(sku.getId())
                .optionValueList(sku.getOptions().stream()
                        .map(ProductOption::getValue)
                        .toList())
                .quantity(sku.getQuantity())
                .additionalPrice(sku.getAdditionalPrice())
                .build();
    }
}

