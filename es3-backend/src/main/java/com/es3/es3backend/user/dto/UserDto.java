package com.es3.es3backend.user.dto;


import com.es3.es3backend.security.EncryptionUtil;
import com.es3.es3backend.user.domain.User;
import lombok.Builder;

public record UserDto(
        Long id,
        String email,
        String name,
        String password,
        String mobile,
        String address,
        String profile_img
){

    @Builder
    public UserDto {
    }

    public static UserDto fromEntity(User user) {
        return UserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .address(user.getAddress())
                .password(user.getPassword())
                .profile_img(user.getProfileImage())
                .mobile(user.getMobile())
                .build();
    }

    public boolean verifyPassword(String rawPassword) throws Exception {
        return rawPassword.equals(EncryptionUtil.decrypt(this.password));
    }
}
