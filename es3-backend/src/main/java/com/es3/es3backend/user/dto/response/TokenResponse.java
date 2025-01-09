package com.es3.es3backend.user.dto.response;

public record TokenResponse(
        String accessToken,
        String refreshToken
) {
}
