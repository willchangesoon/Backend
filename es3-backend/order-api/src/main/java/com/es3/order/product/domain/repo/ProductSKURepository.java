package com.es3.order.product.domain.repo;

import com.es3.order.product.domain.ProductSKU;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductSKURepository extends JpaRepository<ProductSKU, Long> {
    List<ProductSKU> findAllByProductId(Long productId);
}
