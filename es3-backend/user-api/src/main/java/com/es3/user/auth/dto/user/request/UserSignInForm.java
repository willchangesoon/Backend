package com.es3.user.auth.dto.user.request;

public record UserSignInForm(
        String email,
        String password
) {
}
