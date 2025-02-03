package com.es3.es3backend.auth.dto.common.response;

public record TokenResponse(
        String accessToken,
        String refreshToken
) {
}
