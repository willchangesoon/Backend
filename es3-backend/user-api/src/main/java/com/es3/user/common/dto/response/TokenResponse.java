package com.es3.user.common.dto.response;

public record TokenResponse(
        String accessToken,
        String refreshToken
) {
}
