package com.es3.es3backend.seller.dto.response;

import com.es3.es3backend.seller.dto.SellerDto;
import lombok.Builder;

@Builder
public record SignInSellerResponse(
	String email
) {

	public static SignInSellerResponse from(SellerDto sellerDto) {
		return SignInSellerResponse.builder()
			.email(sellerDto.email())
			.build();
	}
}
