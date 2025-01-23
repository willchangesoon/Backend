package com.es3.es3backend.seller.dto.response;

import com.es3.es3backend.seller.dto.SellerDto;
import java.time.LocalDateTime;
import lombok.Builder;

@Builder
public record SellerUpdateResponse(
	LocalDateTime updateDateTime
) {

	public static SellerUpdateResponse from(SellerDto dto) {
		return SellerUpdateResponse.builder()
			.updateDateTime(dto.updateDt()).build();
	}
}
