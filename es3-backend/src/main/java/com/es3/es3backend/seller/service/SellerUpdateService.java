package com.es3.es3backend.seller.service;

import com.es3.es3backend.auth.dto.seller.request.SellerUpdateRequest;
import com.es3.es3backend.config.exception.AuthException;
import com.es3.es3backend.config.exception.ErrorCode;
import com.es3.es3backend.seller.domain.Seller;
import com.es3.es3backend.seller.domain.SellerRepository;
import com.es3.es3backend.seller.dto.SellerDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SellerUpdateService {

	private final SellerRepository sellerRepository;

	@Transactional
	public SellerDto updateEmail(SellerUpdateRequest.Email request, String userEmail) {
		Seller seller = sellerRepository.findByEmail(userEmail).orElseThrow(
			() -> new AuthException(ErrorCode.USER_NOT_FOUND)
		);
		if (sellerRepository.findByEmail(request.email()).isPresent()) {
			throw new AuthException(ErrorCode.REGISTERED_EMAIL);
		}
		return SellerDto.fromEntity(sellerRepository.save(
			Seller.builder()
				.id(seller.getId())
				.email(request.email())
				.build()));
	}

	@Transactional
	public SellerDto updateName(SellerUpdateRequest.Name request, String userEmail) {
		Seller seller = sellerRepository.findByEmail(userEmail).orElseThrow(
			() -> new AuthException(ErrorCode.USER_NOT_FOUND)
		);
		return SellerDto.fromEntity(sellerRepository.save(
			Seller.builder()
				.id(seller.getId())
				.name(request.name())
				.build()));
	}

	@Transactional
	public SellerDto updateMobile(SellerUpdateRequest.Mobile request, String userEmail) {
		Seller seller = sellerRepository.findByEmail(userEmail).orElseThrow(
			() -> new AuthException(ErrorCode.USER_NOT_FOUND)
		);
		if (sellerRepository.findByMobile(request.mobile()).isPresent()) {
			throw new AuthException(ErrorCode.REGISTERED_EMAIL);
		}
		return SellerDto.fromEntity(sellerRepository.save(
			Seller.builder()
				.id(seller.getId())
				.mobile(request.mobile())
				.build()));
	}

	@Transactional
	public SellerDto updateAddress(SellerUpdateRequest.Address request, String userEmail) {
		Seller seller = sellerRepository.findByEmail(userEmail).orElseThrow(
			() -> new AuthException(ErrorCode.USER_NOT_FOUND)
		);
		return SellerDto.fromEntity(sellerRepository.save(
			Seller.builder()
				.id(seller.getId())
				.address(request.address())
				.build()));
	}

}
