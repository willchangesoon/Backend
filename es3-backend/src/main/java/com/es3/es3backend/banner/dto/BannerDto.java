package com.es3.es3backend.banner.dto;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record BannerDto (
        String imageLink,
        LocalDateTime startDt,
        LocalDateTime endDt
) {
}
