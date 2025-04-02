package com.es3.order.product.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductSearchCond {
    private Long categoryId;
    private Long storeId;
    private Boolean discounted;
    private Long cursorId; // 커서 기반 페이지네이션
    private SortDirection sortDirection; // ASC, DESC 등
}



