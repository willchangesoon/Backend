package com.es3.es3backend.auth.dto.user.request;

public record UserSignUpForm(
        String email,
        String name,
        String password,
        String mobile,
        String address,
        String profileImg
) {
}
