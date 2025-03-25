package com.es3.user.seller.controller;

import com.es3.user.common.dto.response.TokenResponse;
import com.es3.user.constants.Role;
import com.es3.user.security.JwtUtil;
import com.es3.user.seller.dto.SellerDto;
import com.es3.user.seller.dto.request.SellerSignInForm;
import com.es3.user.seller.dto.request.SellerSignUpForm;
import com.es3.user.seller.service.SellerAuthService;
import com.es3.user.seller.service.SellerReadService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/oauth/sellers")
public class SellerAuthController {
    private final SellerAuthService sellerAuthService;
    private final SellerReadService sellerReadService;
    private final JwtUtil jwtUtil;

    @PostMapping
    public ResponseEntity<TokenResponse> signUp(@RequestBody SellerSignUpForm form) throws Exception {
        SellerDto sellerDto = sellerAuthService.signUp(form);
        return ResponseEntity.status(200).body(jwtUtil.generateTokens(sellerDto.id(), sellerDto.email(), Role.SELLER));
    }

    @PostMapping("/sign-in")
    public ResponseEntity<TokenResponse> signIn(@RequestBody SellerSignInForm form) throws Exception {
        SellerDto sellerDto = sellerAuthService.signIn(form);
        return ResponseEntity.status(200).body(jwtUtil.generateTokens(sellerDto.id(), sellerDto.email(), Role.SELLER));
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refreshAccessToken(@RequestBody Map<String, String> request) {
        String refreshToken = request.get("refreshToken");
        if (!jwtUtil.validateToken(refreshToken)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid refresh token");
        }
        String sellerId = jwtUtil.getUserId(refreshToken);
        SellerDto seller = sellerReadService.getDetail(sellerId);

        String newAccessToken = jwtUtil.generateAccessToken(seller.id(), seller.email(), seller.role());
        return ResponseEntity.ok(Map.of("accessToken", newAccessToken));
    }
}
