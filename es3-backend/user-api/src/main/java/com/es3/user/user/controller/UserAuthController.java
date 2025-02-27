package com.es3.user.user.controller;


import com.es3.user.common.dto.response.TokenResponse;
import com.es3.user.constants.Role;
import com.es3.user.security.JwtUtil;
import com.es3.user.user.dto.UserDto;
import com.es3.user.user.dto.request.UserSignInForm;
import com.es3.user.user.dto.request.UserSignUpForm;
import com.es3.user.user.service.UserAuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/oauth/users")
public class UserAuthController {
    private final UserAuthService userJoinService;
    private final JwtUtil jwtUtil;

    @PostMapping
    public ResponseEntity signUp(@RequestBody UserSignUpForm request) throws Exception {
         userJoinService.signUp(request);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/sign-in")
    public ResponseEntity<TokenResponse> signIn(@RequestBody UserSignInForm request) throws Exception {
        UserDto userDto = userJoinService.login(request.email(), request.password());
        return ResponseEntity.status(200).body(jwtUtil.generateTokens(userDto.id(), userDto.email(), Role.USER));
    }
}
