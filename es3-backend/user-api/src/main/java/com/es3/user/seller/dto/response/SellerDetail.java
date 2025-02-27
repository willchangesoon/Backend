package com.es3.user.seller.dto.response;

import com.es3.user.seller.dto.SellerDto;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record SellerDetail(
	String email,
	String name,
	String mobile,
	boolean sellerStatus,
	LocalDateTime createDt,
	LocalDateTime updateDt
) {
	public static SellerDetail from(SellerDto sellerDto) {
		return SellerDetail.builder()
			.email(sellerDto.email())
			.name(sellerDto.name())
			.mobile(sellerDto.mobile())
			.sellerStatus(sellerDto.sellerStatus())
			.createDt(sellerDto.createDt())
			.updateDt(sellerDto.updateDt())
			.build();
	}
}
