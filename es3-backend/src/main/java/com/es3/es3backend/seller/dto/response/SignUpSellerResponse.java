package com.es3.es3backend.seller.dto.response;

import com.es3.es3backend.seller.dto.SellerDto;
import lombok.Builder;

@Builder
public record SignUpSellerResponse(
	String name,
	String mobile,
	String address,
	boolean sellerStatus,
	String brn
) {
	public static SignUpSellerResponse from(SellerDto sellerDto) {
		return SignUpSellerResponse.builder()
			.name(sellerDto.name())
			.mobile(sellerDto.mobile())
			.address(sellerDto.address())
			.sellerStatus(sellerDto.sellerStatus())
			.brn(sellerDto.brn())
			.build();
	}
}
