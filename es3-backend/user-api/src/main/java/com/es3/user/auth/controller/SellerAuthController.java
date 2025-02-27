package com.es3.user.auth.controller;

import com.es3.user.auth.dto.common.response.TokenResponse;
import com.es3.user.auth.dto.seller.request.SellerSignInForm;
import com.es3.user.auth.dto.seller.request.SellerSignUpForm;
import com.es3.user.auth.security.JwtUtil;
import com.es3.user.auth.service.SellerAuthService;
import com.es3.user.constants.Role;
import com.es3.user.seller.dto.SellerDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/oauth/sellers")
public class SellerAuthController {
    private final SellerAuthService sellerAuthService;
    private final JwtUtil jwtUtil;

    @PostMapping
    public ResponseEntity signUp(@RequestBody SellerSignUpForm form) throws Exception {
        sellerAuthService.signUp(form);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/sign-in")
    public ResponseEntity<TokenResponse> signIn(@RequestBody SellerSignInForm form) throws Exception {
        SellerDto sellerDto = sellerAuthService.signIn(form);
        return ResponseEntity.status(200).body(jwtUtil.generateTokens(sellerDto.id(), sellerDto.email(), Role.SELLER));
    }
}
