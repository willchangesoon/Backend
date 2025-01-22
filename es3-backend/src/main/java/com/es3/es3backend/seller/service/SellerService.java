package com.es3.es3backend.seller.service;

import com.es3.es3backend.config.exception.AuthException;
import com.es3.es3backend.config.exception.ErrorCode;
import com.es3.es3backend.security.EncryptionUtil;
import com.es3.es3backend.security.JwtUtil;
import com.es3.es3backend.seller.domain.Seller;
import com.es3.es3backend.seller.domain.SellerRepository;
import com.es3.es3backend.seller.dto.SellerDto;
import com.es3.es3backend.seller.dto.request.SellerSignInForm;
import com.es3.es3backend.seller.dto.request.SellerSignUpForm;
import com.es3.es3backend.user.dto.response.TokenResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SellerService implements UserDetailsService {

	private final SellerRepository sellerRepository;
	private final JwtUtil jwtUtil;

	public SellerDto signUp(SellerSignUpForm form) throws Exception {

		validationCheck(form);

		return SellerDto.fromEntity(
			sellerRepository.save(Seller.builder()
				.email(form.email())
				.password(EncryptionUtil.encrypt(form.password()))
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

	public TokenResponse signIn(SellerSignInForm form) throws Exception {
		Seller seller = sellerRepository.findByEmail(form.email())
			.orElseThrow(() -> new AuthException(ErrorCode.USER_NOT_FOUND));

		seller.verifyPassword(form.password());

		return jwtUtil.generateTokens(seller.getId(), seller.getEmail());
	}

	private void validationCheck(SellerSignUpForm form) {
		if (validationEmailCheck(form.email())) {
			throw new AuthException(ErrorCode.INVALID_EMAIL);
		}
		if (validationMobileCheck(form.mobile())) {
			throw new AuthException(ErrorCode.REGISTERED_MOBILE);
		}
	}

	private boolean validationMobileCheck(String mobile) {
		return sellerRepository.findByMobile(mobile).isPresent();
	}

	private boolean validationEmailCheck(String email) {
		return sellerRepository.findByEmail(email).isPresent();
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return sellerRepository.findByEmail(username).orElseThrow(
			() -> new AuthException(ErrorCode.USER_NOT_FOUND)
		);
	}
}
