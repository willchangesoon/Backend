package com.es3.user.user.controller;

import com.es3.user.user.dto.UserDto;
import com.es3.user.user.dto.request.UpdateRequest;
import com.es3.user.user.service.UserUpdateService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/users")
@Tag(name = "유저", description = "유저 API 입니다")
public class UserController {
    private final UserUpdateService userUpdateService;

    @GetMapping("")
    @Operation(summary = "유저 정보", description = "유저 정보 API 입니다.")
    public ResponseEntity<UserDto> getUserInfo(@RequestHeader("X-User-Id") String userId) {
        return ResponseEntity.ok(UserDto.fromEntity(userUpdateService.getUser(userId)));
    }

    @PatchMapping("/update-email")
    @Operation(summary = "유저 이메일 수정" , description = "유저 이메일 정보 수정 API 입니다.")
    public void updateUserEmail(Principal principal, @RequestBody UpdateRequest.Email email) {
        userUpdateService.updateEmail(principal.getName(), email);
    }

    @PatchMapping("/update-mobile")
    @Operation(summary = "유저 전화번호 수정" , description = "유저 전화번호 정보 수정 API 입니다.")
    public void updateUserMobile(Principal principal, @RequestBody UpdateRequest.Mobile mobile) {
        userUpdateService.updateMobile(principal.getName(), mobile);
    }

    @PatchMapping("/update-address")
    @Operation(summary = "유저 주소 수정" , description = "유저 주소 정보 수정 API 입니다.")
    public void updateUserAddress(Principal principal, @RequestBody UpdateRequest.Address address) {
        userUpdateService.updateAddress(principal.getName(), address);
    }

    @PatchMapping("/update-name")
    @Operation(summary = "유저 이름 수정" , description = "유저 이름 정보 수정 API 입니다.")
    public void updateUserName(Principal principal, @RequestBody UpdateRequest.Name name) {
        userUpdateService.updateName(principal.getName(), name);
    }

    @PatchMapping("/update-profile-image")
    @Operation(summary = "유저 프로필 사진 수정" , description = "유저 프로필 사진 정보 수정 API 입니다.")
    public void updateUserProfileImage(Principal principal, @RequestBody UpdateRequest.ProfileImage profileImage) {
        userUpdateService.updateProfileImage(principal.getName(), profileImage);
    }

    @PatchMapping("/update-password")
    @Operation(summary = "유저 비밀번호 수정" , description = "유저 비밀번호 정보 수정 API 입니다.")
    public void updateUserPassword(Principal principal, @RequestBody UpdateRequest.Password password) {
        userUpdateService.updatePassword(principal.getName(), password);
    }
}
