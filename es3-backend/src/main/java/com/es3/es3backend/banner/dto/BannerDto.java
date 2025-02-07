package com.es3.es3backend.banner.dto;

import com.es3.es3backend.banner.domain.Banner;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record BannerDto (
        String imageLink,
        LocalDateTime startDt,
        LocalDateTime endDt
) {
    public static BannerDto fromEntity(Banner banner) {
        return BannerDto.builder()
                .imageLink(banner.getImageLink())
                .startDt(banner.getStartDt())
                .endDt(banner.getEndDt())
                .build();
    }
}
