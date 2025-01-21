package com.es3.es3backend.seller.controller;

import com.es3.es3backend.seller.dto.request.SellerSignUpForm;
import com.es3.es3backend.seller.dto.response.SignUpSellerResponse;
import com.es3.es3backend.seller.service.SellerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/sellers")
public class SellerController {

	private final SellerService sellerService;
	@PostMapping
	public ResponseEntity<SignUpSellerResponse> register(SellerSignUpForm form) {
		return ResponseEntity.status(201).body(
			SignUpSellerResponse.from(sellerService.signUp(form))
		);
	}
}
