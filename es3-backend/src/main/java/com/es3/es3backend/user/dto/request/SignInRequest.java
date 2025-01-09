package com.es3.es3backend.user.dto.request;

public record SignInRequest(
        String email,
        String name,
        String password,
        String mobile,
        String address,
        String profile_img
) {
}
