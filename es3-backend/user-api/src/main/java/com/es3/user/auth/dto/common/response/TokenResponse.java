package com.es3.user.auth.dto.common.response;

public record TokenResponse(
        String accessToken,
        String refreshToken
) {
}
