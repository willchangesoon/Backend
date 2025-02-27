package com.es3.order.category.dto;

import lombok.Builder;

@Builder
public record CategoryDto (
        String name,
        Long parentsId
){
}
