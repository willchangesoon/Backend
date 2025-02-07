package com.es3.es3backend.banner.dto.request;

import java.time.LocalDateTime;

public record BannerUpdateForm(
        ImageLink imageLink,
        StartDt startDt,
        EndDt endDt
) {

    public record ImageLink(
            String imageLink
    ) {
    }

    public record StartDt(
            LocalDateTime startDt
    ) {
    }

    public record EndDt(
            LocalDateTime endDt
    ) {
    }
}
