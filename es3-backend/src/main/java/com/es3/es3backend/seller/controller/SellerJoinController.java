package com.es3.es3backend.seller.controller;

import com.es3.es3backend.seller.dto.request.SellerSignInForm;
import com.es3.es3backend.seller.dto.request.SellerSignUpForm;
import com.es3.es3backend.seller.dto.response.SignUpSellerResponse;
import com.es3.es3backend.seller.service.SellerService;
import com.es3.es3backend.user.dto.response.TokenResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/oauth/sellers")
public class SellerJoinController {

	private final SellerService sellerService;

	@PostMapping
	public ResponseEntity<SignUpSellerResponse> signUp(@RequestBody SellerSignUpForm form) throws Exception {
		return ResponseEntity.status(201).body(
			SignUpSellerResponse.from(sellerService.signUp(form))
		);
	}

	@PutMapping
	public ResponseEntity<TokenResponse> signIn(@RequestBody SellerSignInForm form) throws Exception {
		return ResponseEntity.status(200).body(sellerService.signIn(form));
	}
}
