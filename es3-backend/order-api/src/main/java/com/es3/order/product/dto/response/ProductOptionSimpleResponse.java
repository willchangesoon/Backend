package com.es3.order.product.dto.response;

import com.es3.order.product.domain.ProductOption;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ProductOptionSimpleResponse {
    private Long optionId;
    private String value;

    public static ProductOptionSimpleResponse fromEntity(ProductOption option) {
        return ProductOptionSimpleResponse.builder()
                .optionId(option.getId())
                .value(option.getValue())
                .build();
    }
}

