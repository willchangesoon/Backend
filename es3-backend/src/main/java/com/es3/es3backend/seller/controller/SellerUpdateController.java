package com.es3.es3backend.seller.controller;

import com.es3.es3backend.seller.dto.request.SellerUpdateRequest;
import com.es3.es3backend.seller.dto.response.SellerUpdateResponse;
import com.es3.es3backend.seller.service.SellerUpdateService;
import java.security.Principal;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/sellers/update")
public class SellerUpdateController {

	private final SellerUpdateService sellerUpdateService;
	@PatchMapping("/email")
	public ResponseEntity<?> emailUpdate(@RequestBody SellerUpdateRequest.Email request,
		Principal principal) {
		return ResponseEntity.ok().body(
			SellerUpdateResponse.from(sellerUpdateService.updateEmail(request, principal.getName()))
		);
	}

	@PatchMapping("/name")
	public ResponseEntity<?> nameUpdate(@RequestBody SellerUpdateRequest.Name request,
		Principal principal) {
		return ResponseEntity.ok().body(
			SellerUpdateResponse.from(sellerUpdateService.updateName(request, principal.getName()))
		);
	}

	@PatchMapping("/mobile")
	public ResponseEntity<?> mobileUpdate(@RequestBody SellerUpdateRequest.Mobile request,
		Principal principal) {
		return ResponseEntity.ok().body(
			SellerUpdateResponse.from(sellerUpdateService.updateMobile(request, principal.getName()))
		);
	}

	@PatchMapping("/address")
	public ResponseEntity<?> addressUpdate(@RequestBody SellerUpdateRequest.Address request,
		Principal principal) {
		return ResponseEntity.ok().body(
			SellerUpdateResponse.from(sellerUpdateService.updateAddress(request, principal.getName()))
		);
	}

}
