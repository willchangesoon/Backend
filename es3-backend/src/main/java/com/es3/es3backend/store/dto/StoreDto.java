package com.es3.es3backend.store.dto;

import com.es3.es3backend.store.domain.Store;
import lombok.Builder;

public record StoreDto(
        String name,
        String logoImg,
        String description,
        String address,
        String contactNumber
) {

    @Builder
    public StoreDto {
    }

    public static StoreDto fromEntity(Store store) {
        return StoreDto.builder()
                .name(store.getName())
                .address(store.getAddress())
                .description(store.getDescription())
                .logoImg(store.getLogoImg())
                .contactNumber(store.getContactNumber())
                .build();
    }
}
