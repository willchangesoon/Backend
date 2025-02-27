package com.es3.user.seller.dto.response;

import com.es3.user.seller.dto.SellerDto;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record SellerUpdateResponse(
	LocalDateTime updateDateTime
) {

	public static SellerUpdateResponse from(SellerDto dto) {
		return SellerUpdateResponse.builder()
			.updateDateTime(dto.updateDt()).build();
	}
}
