package com.es3.es3backend.user.dto.request;

public record LoginRequest(
        String email,
        String password
) {
}
