package com.es3.user.seller.dto.request;

public record SellerSignUpForm(
        BasicInfo basicInfo,
        BusinessInfo businessInfo,
        BankInfo bankInfo
) {

    public record BasicInfo(
            String email,
            String password,
            String name,
            String mobile
    ) {
    }

    public record BusinessInfo(
            String representativeName,
            String representativeContact,
            String businessNumber,
            String businessName,
            String businessAddress,
            String businessLicenseFile
    ){
    }

    public record BankInfo(
            String bank,
            String accountNumber,
            String accountHolder,
            String bankbookCopy
    ){
    }
}
