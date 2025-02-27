package com.es3.user.seller.controller;

import com.es3.user.auth.dto.seller.response.SellerDetail;
import com.es3.user.seller.service.SellerReadService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/sellers")
public class SellerReadController {

	private final SellerReadService sellerReadService;
	@GetMapping
	public ResponseEntity<SellerDetail> getDetail(Principal principal) {
		return ResponseEntity.ok().body(
			SellerDetail.from(sellerReadService.getDetail(principal.getName()))
		);
	}
}
