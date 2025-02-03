package com.es3.es3backend.auth.dto.user.request;

public record UserSignInForm(
        String email,
        String password
) {
}
