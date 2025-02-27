package com.es3.user.user.dto;


import com.es3.user.auth.security.EncryptionUtil;
import com.es3.user.user.domain.User;
import lombok.Builder;

public record UserDto(
        Long id,
        String email,
        String name,
        String password,
        String mobile,
        String address,
        String profileImg
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
                .profileImg(user.getProfileImage())
                .mobile(user.getMobile())
                .build();
    }

    public boolean verifyPassword(String rawPassword) throws Exception {
        return rawPassword.equals(EncryptionUtil.decrypt(this.password));
    }
}
