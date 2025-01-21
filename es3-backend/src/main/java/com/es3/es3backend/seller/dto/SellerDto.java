package com.es3.es3backend.seller.dto;

import com.es3.es3backend.seller.domain.Seller;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public record SellerDto (
	Long id,
	String email,
	String name,
	String mobile,
	String postCode,
	String address,
	String brn,
	String bank,
	String accountNumber,
	String accountHolder,
	boolean sellerStatus
) {
	public static SellerDto fromEntity(Seller seller) {
		return SellerDto.builder()
			.id(seller.getId())
			.email(seller.getEmail())
			.name(seller.getName())
			.mobile(seller.getMobile())
			.postCode(seller.getPostCode())
			.address(seller.getAddress())
			.brn(seller.getBrn())
			.bank(seller.getBank())
			.accountNumber(seller.getAccountNumber())
			.accountHolder(seller.getAccountHolder())
			.sellerStatus(seller.isSellerStatus())
			.build();
	}
}
