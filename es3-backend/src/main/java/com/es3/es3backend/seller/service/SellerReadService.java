package com.es3.es3backend.seller.service;

import com.es3.es3backend.config.exception.AuthException;
import com.es3.es3backend.config.exception.ErrorCode;
import com.es3.es3backend.seller.domain.SellerRepository;
import com.es3.es3backend.seller.dto.SellerDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SellerReadService {

	private final SellerRepository sellerRepository;

	public SellerDto getDetail(String email) {
		return SellerDto.fromEntity(sellerRepository.findByEmail(email).orElseThrow(
			() -> new AuthException(ErrorCode.USER_NOT_FOUND)
		));
	}
}
