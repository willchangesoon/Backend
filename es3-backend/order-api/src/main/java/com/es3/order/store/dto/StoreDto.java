package com.es3.order.store.dto;

import com.es3.order.banner.dto.BannerDto;
import com.es3.order.store.domain.Store;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record StoreDto(
        String name,
        String logoImg,
        String description,
        String address,
        String contactNumber,
        List<BannerDto> bannerList
) {

    public static StoreDto fromEntityFilterBanner(Store store) {
        return StoreDto.builder()
                .name(store.getName())
                .description(store.getDescription())
                .logoImg(store.getLogoImg())
                .contactNumber(store.getContactNumber())
                .bannerList(
                        store.getBanners().stream().filter(
                                banner -> {
                                    LocalDateTime now = LocalDateTime.now();
                                    return (banner.getStartDt().isBefore(now) || banner.getStartDt().isEqual(now)) &&
                                            (banner.getEndDt().isAfter(now) || banner.getEndDt().isEqual(now));
                                }
                        ).map(BannerDto::fromEntity).toList()
                )
                .build();
    }

    public static StoreDto fromEntity(Store store) {
        return StoreDto.builder()
                .name(store.getName())
                .description(store.getDescription())
                .logoImg(store.getLogoImg())
                .contactNumber(store.getContactNumber())
                .bannerList(store.getBanners().stream().map(BannerDto::fromEntity).toList())
                .build();
    }
}
