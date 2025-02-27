package com.es3.user.user.dto.request;

public record UserSignUpForm(
        String email,
        String name,
        String password,
        String mobile,
        String address,
        String profileImg
) {
}
