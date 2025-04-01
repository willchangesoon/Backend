package com.es3.order.product.domain.repo;

import com.es3.order.product.domain.ProductOptionGroup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductOptionGroupRepository extends JpaRepository<ProductOptionGroup, Long> {
    List<ProductOptionGroup> findAllByProductId(Long productId);
}
