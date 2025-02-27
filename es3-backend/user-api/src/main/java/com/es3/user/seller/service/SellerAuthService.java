package com.es3.user.seller.service;

import com.es3.user.config.exception.AuthException;
import com.es3.user.config.exception.ErrorCode;
import com.es3.user.seller.domain.Seller;
import com.es3.user.seller.domain.SellerBankInfo;
import com.es3.user.seller.domain.SellerBusinessInfo;
import com.es3.user.seller.domain.repo.SellerBankInfoRepository;
import com.es3.user.seller.domain.repo.SellerBusinessInfoRepository;
import com.es3.user.seller.domain.repo.SellerRepository;
import com.es3.user.seller.dto.SellerDto;
import com.es3.user.seller.dto.request.SellerSignInForm;
import com.es3.user.seller.dto.request.SellerSignUpForm;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class SellerAuthService {
    private final SellerRepository sellerRepository;
    private final SellerBankInfoRepository bankInfoRepository;
    private final SellerBusinessInfoRepository businessInfoRepository;

    public SellerDto signUp(SellerSignUpForm form) throws Exception {
        validationCheck(form);
        Seller seller = sellerRepository.save(Seller.createSeller(form.basicInfo()));
        bankInfoRepository.save(SellerBankInfo.create(seller.getId(), form.bankInfo()));
        businessInfoRepository.save(SellerBusinessInfo.create(seller.getId(), form.businessInfo()));
        return SellerDto.fromEntity(sellerRepository.save(seller));
    }

    public SellerDto signIn(SellerSignInForm form) throws Exception {
        Seller seller = sellerRepository.findByEmail(form.email())
                .orElseThrow(() -> new AuthException(ErrorCode.USER_NOT_FOUND));

        seller.verifyPassword(form.password());

        return SellerDto.fromEntity(seller);
    }

    private void validationCheck(SellerSignUpForm form) {
        if (validationEmailCheck(form.basicInfo().email())) {
            throw new AuthException(ErrorCode.INVALID_EMAIL);
        }
        if (validationMobileCheck(form.basicInfo().mobile())) {
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
