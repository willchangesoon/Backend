package com.es3.es3backend.seller.controller;

import com.es3.es3backend.seller.dto.response.SellerDetail;
import com.es3.es3backend.seller.service.SellerReadService;
import java.security.Principal;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
