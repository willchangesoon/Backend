package com.es3.order.category.dto;

import com.es3.order.category.domain.Category;
import lombok.Builder;

@Builder
public record CategoryDto (
        Long id,
        String name,
        Long parentsId
){
    public static CategoryDto fromEntity(Category category) {
        return CategoryDto.builder()
                .id(category.getId())
                .name(category.getName())
                .parentsId(category.getParentsId())
                .build();
    }
}
