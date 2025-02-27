package com.es3.user.seller.dto;

import com.es3.user.constants.Role;
import com.es3.user.seller.domain.Seller;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record SellerDto (
	Long id,
	String email,
	String name,
	String mobile,
	Role role,
	boolean sellerStatus,
	LocalDateTime createDt,
	LocalDateTime updateDt
) {
	public static SellerDto fromEntity(Seller seller) {
		return SellerDto.builder()
			.id(seller.getId())
			.email(seller.getEmail())
			.name(seller.getName())
			.mobile(seller.getMobile())
			.sellerStatus(seller.isSellerStatus())
			.createDt(seller.getCreatedDate())
			.updateDt(seller.getUpdateDate())
			.role(seller.getRole())
			.build();
	}
}
