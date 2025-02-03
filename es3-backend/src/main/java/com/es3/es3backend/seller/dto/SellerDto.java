package com.es3.es3backend.seller.dto;

import com.es3.es3backend.constants.Role;
import com.es3.es3backend.seller.domain.Seller;
import java.time.LocalDateTime;
import lombok.Builder;

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
	String idNumber,
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
			.postCode(seller.getPostCode())
			.address(seller.getAddress())
			.brn(seller.getBrn())
			.bank(seller.getBank())
			.accountNumber(seller.getAccountNumber())
			.accountHolder(seller.getAccountHolder())
			.idNumber(seller.getIdNumber())
			.sellerStatus(seller.isSellerStatus())
			.createDt(seller.getCreatedDate())
			.updateDt(seller.getUpdateDate())
			.role(seller.getRole())
			.build();
	}
}
