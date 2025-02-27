package com.es3.user.seller.service;

import com.es3.user.config.exception.AuthException;
import com.es3.user.config.exception.ErrorCode;
import com.es3.user.seller.domain.repo.SellerRepository;
import com.es3.user.seller.dto.SellerDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SellerReadService {

	private final SellerRepository sellerRepository;

	public SellerDto getDetail(String sellerId) {
		return SellerDto.fromEntity(sellerRepository.findById(Long.parseLong(sellerId)).orElseThrow(
			() -> new AuthException(ErrorCode.USER_NOT_FOUND)
		));
	}
}
