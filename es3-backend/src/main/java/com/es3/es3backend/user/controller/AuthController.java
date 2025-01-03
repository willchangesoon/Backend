package com.es3.es3backend.user.controller;

import com.es3.es3backend.security.JwtUtil;
import com.es3.es3backend.user.dto.UserDto;
import com.es3.es3backend.user.dto.request.LoginRequest;
import com.es3.es3backend.user.dto.request.SignInRequest;
import com.es3.es3backend.user.dto.response.TokenResponse;
import com.es3.es3backend.user.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/oauth")
public class AuthController {
    private final AuthService authService;
    private final JwtUtil jwtUtil;

    @PostMapping("/signUp")
    public TokenResponse login(@RequestBody SignInRequest request) throws Exception {
        UserDto userDto = authService.signUp(request);
        return jwtUtil.generateTokens(userDto.id(), userDto.email());
    }

    @PostMapping("/login")
    public TokenResponse login(@RequestBody LoginRequest request) throws Exception {
        UserDto userDto = authService.login(request.email(), request.password());
        return jwtUtil.generateTokens(userDto.id(), userDto.email());
    }
}
