package com.es3.user.seller.controller;


import com.es3.user.auth.dto.seller.request.SellerUpdateRequest;
import com.es3.user.auth.dto.seller.response.SellerUpdateResponse;
import com.es3.user.seller.service.SellerUpdateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/sellers/update")
public class SellerUpdateController {

	private final SellerUpdateService sellerUpdateService;
	@PatchMapping("/email")
	public ResponseEntity<SellerUpdateResponse> emailUpdate(@RequestBody SellerUpdateRequest.Email request,
															Principal principal) {
		return ResponseEntity.ok().body(
			SellerUpdateResponse.from(sellerUpdateService.updateEmail(request, principal.getName()))
		);
	}

	@PatchMapping("/name")
	public ResponseEntity<SellerUpdateResponse> nameUpdate(@RequestBody SellerUpdateRequest.Name request,
		Principal principal) {
		return ResponseEntity.ok().body(
			SellerUpdateResponse.from(sellerUpdateService.updateName(request, principal.getName()))
		);
	}

	@PatchMapping("/mobile")
	public ResponseEntity<SellerUpdateResponse> mobileUpdate(@RequestBody SellerUpdateRequest.Mobile request,
		Principal principal) {
		return ResponseEntity.ok().body(
			SellerUpdateResponse.from(sellerUpdateService.updateMobile(request, principal.getName()))
		);
	}

	@PatchMapping("/address")
	public ResponseEntity<SellerUpdateResponse> addressUpdate(@RequestBody SellerUpdateRequest.Address request,
		Principal principal) {
		return ResponseEntity.ok().body(
			SellerUpdateResponse.from(sellerUpdateService.updateAddress(request, principal.getName()))
		);
	}

}
