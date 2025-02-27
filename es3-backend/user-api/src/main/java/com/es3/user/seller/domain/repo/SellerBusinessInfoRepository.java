package com.es3.user.seller.domain.repo;

import com.es3.user.seller.domain.SellerBusinessInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SellerBusinessInfoRepository extends JpaRepository<SellerBusinessInfo, Long> {
}
