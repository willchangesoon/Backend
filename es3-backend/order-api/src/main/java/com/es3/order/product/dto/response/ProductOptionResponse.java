package com.es3.order.product.dto.response;

import com.es3.order.product.domain.ProductOption;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ProductOptionResponse {
    private Long optionId;
    private String groupName;  // 🔄 name → groupName 으로 변경
    private String value;

    public static ProductOptionResponse fromEntity(ProductOption option) {
        return ProductOptionResponse.builder()
                .optionId(option.getId())
                .groupName(option.getOptionGroup().getName()) // 그룹에서 name 가져오기
                .value(option.getValue())
                .build();
    }
}
