package com.es3.user.seller.domain;

import com.es3.user.common.entity.BaseEntity;
import com.es3.user.seller.dto.request.SellerSignUpForm;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EntityListeners(AuditingEntityListener.class)
@Table(name = "tb_seller_bank_info")
public class SellerBankInfo extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "seller_id", nullable = false)
    private Long sellerId;
    @Column(name = "bank", nullable = false)
    private String bank;
    @Column(name = "account_number", nullable = false)
    private String accountNumber;
    @Column(name = "account_holder", nullable = false)
    private String accountHolder;
    @Column(name = "bankbook_copy", nullable = false)
    private String bankboookCopy;

    public static SellerBankInfo create(Long sellerId, SellerSignUpForm.BankInfo bankInfo) {
        return new SellerBankInfo(null, sellerId, bankInfo.bank(), bankInfo.accountNumber(), bankInfo.accountHolder(), bankInfo.bankbookCopy());
    }
}
