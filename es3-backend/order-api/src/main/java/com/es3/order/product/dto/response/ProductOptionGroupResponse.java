package com.es3.order.product.dto.response;

import com.es3.order.product.domain.ProductOptionGroup;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class ProductOptionGroupResponse {
    private String groupName;
    private List<ProductOptionSimpleResponse> options;

    public static ProductOptionGroupResponse fromEntity(ProductOptionGroup group) {
        return ProductOptionGroupResponse.builder()
                .groupName(group.getName())
                .options(group.getOptions().stream()
                        .map(ProductOptionSimpleResponse::fromEntity)
                        .toList())
                .build();
    }
}
