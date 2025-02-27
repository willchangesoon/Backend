package com.es3.user.seller.service;

import com.es3.user.config.exception.AuthException;
import com.es3.user.config.exception.ErrorCode;
import com.es3.user.constants.Role;
import com.es3.user.security.EncryptionUtil;
import com.es3.user.seller.domain.Seller;
import com.es3.user.seller.domain.SellerRepository;
import com.es3.user.seller.dto.SellerDto;
import com.es3.user.seller.dto.request.SellerSignInForm;
import com.es3.user.seller.dto.request.SellerSignUpForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SellerAuthService {
    private final SellerRepository sellerRepository;

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
                        .idNumber(form.idNumber())
                        .role(Role.SELLER)
                        .build()));
    }

    public SellerDto signIn(SellerSignInForm form) throws Exception {
        Seller seller = sellerRepository.findByEmail(form.email())
                .orElseThrow(() -> new AuthException(ErrorCode.USER_NOT_FOUND));

        seller.verifyPassword(form.password());

        return SellerDto.fromEntity(seller);
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


}
