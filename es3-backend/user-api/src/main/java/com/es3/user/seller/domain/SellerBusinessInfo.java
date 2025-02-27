package com.es3.user.seller.domain;

import com.es3.user.common.entity.BaseEntity;
import com.es3.user.seller.dto.request.SellerSignUpForm;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EntityListeners(AuditingEntityListener.class)
@Table(name = "tb_seller_business_info")
public class SellerBusinessInfo extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "seller_id", nullable = false)
    private Long sellerId;

    @Column(name = "representative_name", nullable = false)
    private String representativeName;

    @Column(name = "representative_contact", nullable = false)
    private String representativeContact;

    @Column(name = "business_number", nullable = false)
    private String businessNumber;

    @Column(name = "business_name", nullable = false)
    private String businessName;

    @Column(name = "business_address", nullable = false)
    private String businessAddress;

    @Column(name = "business_license_file", nullable = false)
    private String businessLicenseFile;

    @Builder
    public SellerBusinessInfo(Long sellerId, String representativeName, String representativeContact, String businessNumber, String businessName, String businessAddress, String businessLicenseFile) {
        this.sellerId = sellerId;
        this.representativeName = representativeName;
        this.representativeContact = representativeContact;
        this.businessNumber = businessNumber;
        this.businessName = businessName;
        this.businessAddress = businessAddress;
        this.businessLicenseFile = businessLicenseFile;
    }

    public static SellerBusinessInfo create(Long sellerId, SellerSignUpForm.BusinessInfo businessInfo) {
        return SellerBusinessInfo.builder()
                .sellerId(sellerId)
                .representativeName(businessInfo.representativeName())
                .representativeContact(businessInfo.representativeContact())
                .businessNumber(businessInfo.businessNumber())
                .businessName(businessInfo.businessName())
                .businessAddress(businessInfo.businessAddress())
                .businessLicenseFile(businessInfo.businessLicenseFile())
                .build();
    }
}
