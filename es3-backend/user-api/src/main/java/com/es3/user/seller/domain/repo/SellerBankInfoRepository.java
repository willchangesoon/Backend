package com.es3.user.seller.domain.repo;

import com.es3.user.seller.domain.SellerBankInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SellerBankInfoRepository extends JpaRepository<SellerBankInfo, Long> {
}
