package com.es3.user.user.dto.request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

public class UpdateRequest {

    @Getter
    public static class Email{
        @NotBlank(message = "Email cannot be empty")
        private String email;
    }

    @Getter
    public static class Name {
        @NotBlank(message = "Name cannot be empty")
        @Size(max = 50, message = "Name cannot exceed 50 characters")
        private String name;
    }

    @Getter
    public static class Address {
        @NotBlank(message = "Address cannot be empty")
        private String address;
    }

    @Getter
    public static class Password {
        @NotBlank(message = "Password cannot be empty")
        @Size(min = 8, max = 100, message = "Password must be between 8 and 100 characters")
        private String newPassword;

        @NotBlank(message = "Old password cannot be empty")
        private String oldPassword;
    }

    @Getter
    public static class ProfileImage {
        @NotBlank(message = "ProfileImage cannot be empty")
        private String profileImage;
    }

    @Getter
    public static class Mobile {
        @NotBlank(message = "Mobile cannot be empty")
        private String mobile;
    }
}
