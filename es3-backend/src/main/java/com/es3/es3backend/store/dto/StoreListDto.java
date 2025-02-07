package com.es3.es3backend.store.dto;

import com.es3.es3backend.store.domain.Store;
import lombok.Builder;

import java.util.List;

@Builder
public record StoreListDto(
        List<StoreElementDto> storeList
) {

    public static StoreListDto fromEntity(List<Store> stores) {
        return StoreListDto.builder().storeList(stores.stream().map(e ->
                        StoreElementDto.builder()
                                .name(e.getName())
                                .logoImg(e.getLogoImg())
                                .id(e.getId())
                                .build()).toList())
                .build();
    }

    @Builder
    public record StoreElementDto(
            Long id,
            String name,
            String logoImg
    ) {
    }
}
