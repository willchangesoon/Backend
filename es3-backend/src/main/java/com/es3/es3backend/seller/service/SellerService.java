package com.es3.es3backend.seller.service;

import com.es3.es3backend.seller.domain.Seller;
import com.es3.es3backend.seller.domain.SellerRepository;
import com.es3.es3backend.seller.dto.SellerDto;
import com.es3.es3backend.seller.dto.request.SellerSignUpForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SellerService {

	private final SellerRepository sellerRepository;

	public SellerDto signUp(SellerSignUpForm form) {

		validationCheck(form);

		return SellerDto.fromEntity(
			sellerRepository.save(Seller.builder()
				.email(form.email())
				.password(form.password())
				.name(form.name())
				.mobile(form.mobile())
				.postCode(form.postCode())
				.address(form.address())
				.brn(form.brn())
				.bank(form.bank())
				.accountNumber(form.accountNumber())
				.accountHolder(form.accountHolder())
				.build()));
	}

	private void validationCheck(SellerSignUpForm form) {
		if (validationEmailCheck(form.email())) {
			throw new RuntimeException("중복 이메일");
		}
		if (validationMobileCheck(form.mobile())) {
			throw new RuntimeException("중복 모바일 번호");
		}
	}

	private boolean validationMobileCheck(String mobile) {
		return sellerRepository.findByMobile(mobile).isPresent();
	}

	private boolean validationEmailCheck(String email) {
		return sellerRepository.findByEmail(email).isPresent();
	}
}
