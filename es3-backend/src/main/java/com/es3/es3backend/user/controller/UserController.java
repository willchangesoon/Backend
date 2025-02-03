package com.es3.es3backend.user.controller;

import com.es3.es3backend.user.domain.User;
import com.es3.es3backend.user.dto.UserDto;
import com.es3.es3backend.user.dto.request.UpdateRequest;
import com.es3.es3backend.user.service.UserUpdateService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/users")
@Tag(name = "유저", description = "유저 API 입니다")
public class UserController {
    private final UserUpdateService userUpdateService;

    @GetMapping("")
    @Operation(summary = "유저 정보", description = "유저 정보 API 입니다.")
    public ResponseEntity<UserDto> getUserInfo(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(UserDto.fromEntity(user));
    }

    @PatchMapping("/update-email")
    @Operation(summary = "유저 이메일 수정" , description = "유저 이메일 정보 수정 API 입니다.")
    public void updateUserEmail(@AuthenticationPrincipal User user, @RequestBody UpdateRequest.Email email) {
        userUpdateService.updateEmail(user, email);
    }

    @PatchMapping("/update-mobile")
    @Operation(summary = "유저 전화번호 수정" , description = "유저 전화번호 정보 수정 API 입니다.")
    public void updateUserMobile(@AuthenticationPrincipal User user, @RequestBody UpdateRequest.Mobile mobile) {
        userUpdateService.updateMobile(user, mobile);
    }

    @PatchMapping("/update-address")
    @Operation(summary = "유저 주소 수정" , description = "유저 주소 정보 수정 API 입니다.")
    public void updateUserAddress(@AuthenticationPrincipal User user, @RequestBody UpdateRequest.Address address) {
        userUpdateService.updateAddress(user, address);
    }

    @PatchMapping("/update-name")
    @Operation(summary = "유저 이름 수정" , description = "유저 이름 정보 수정 API 입니다.")
    public void updateUserName(@AuthenticationPrincipal User user, @RequestBody UpdateRequest.Name name) {
        userUpdateService.updateName(user, name);
    }

    @PatchMapping("/update-profile-image")
    @Operation(summary = "유저 프로필 사진 수정" , description = "유저 프로필 사진 정보 수정 API 입니다.")
    public void updateUserProfileImage(@AuthenticationPrincipal User user, @RequestBody UpdateRequest.ProfileImage profileImage) {
        userUpdateService.updateProfileImage(user, profileImage);
    }

    @PatchMapping("/update-password")
    @Operation(summary = "유저 비밀번호 수정" , description = "유저 비밀번호 정보 수정 API 입니다.")
    public void updateUserPassword(@AuthenticationPrincipal User user, @RequestBody UpdateRequest.Password password) {
        userUpdateService.updatePassword(user, password);
    }
}
