package com.es3.user.user.dto.request;

public record UserSignInForm(
        String email,
        String password
) {
}
