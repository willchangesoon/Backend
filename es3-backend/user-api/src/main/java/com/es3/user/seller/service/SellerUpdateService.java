package com.es3.user.seller.service;

import com.es3.user.config.exception.AuthException;
import com.es3.user.config.exception.ErrorCode;
import com.es3.user.seller.domain.Seller;
import com.es3.user.seller.domain.SellerRepository;
import com.es3.user.seller.dto.SellerDto;
import com.es3.user.seller.dto.request.SellerUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class SellerUpdateService {

	private final SellerRepository sellerRepository;


	public SellerDto updateEmail(SellerUpdateRequest.Email request, String sellerId) {
		Seller seller = getSeller(sellerId);
		if (sellerRepository.findByEmail(request.email()).isPresent()) {
			throw new AuthException(ErrorCode.REGISTERED_EMAIL);
		}
		return SellerDto.fromEntity(seller.updateEmail(request.email()));
	}


	@Transactional
	public SellerDto updateName(SellerUpdateRequest.Name request, String sellerId) {
		Seller seller = getSeller(sellerId);
		return SellerDto.fromEntity(seller.updateName(request.name()));
	}

	@Transactional
	public SellerDto updateMobile(SellerUpdateRequest.Mobile request, String sellerId) {
		Seller seller = getSeller(sellerId);
		if (sellerRepository.findByMobile(request.mobile()).isPresent()) {
			throw new AuthException(ErrorCode.REGISTERED_EMAIL);
		}
		return SellerDto.fromEntity(seller.updateMobile(request.mobile()));
	}

	@Transactional
	public SellerDto updateAddress(SellerUpdateRequest.Address request, String sellerId) {
		Seller seller = getSeller(sellerId);
		return SellerDto.fromEntity(seller.updateAddress(request.address()));
	}

	private Seller getSeller(String sellerId) {
		return sellerRepository.findById(Long.valueOf(sellerId)).orElseThrow(
			() -> new AuthException(ErrorCode.USER_NOT_FOUND)
		);
	}

}
