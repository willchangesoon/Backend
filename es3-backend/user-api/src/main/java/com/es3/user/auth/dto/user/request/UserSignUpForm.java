package com.es3.user.auth.dto.user.request;

public record UserSignUpForm(
        String email,
        String name,
        String password,
        String mobile,
        String address,
        String profileImg
) {
}
