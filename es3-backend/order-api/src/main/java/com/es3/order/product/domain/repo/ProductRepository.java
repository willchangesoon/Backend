package com.es3.order.product.domain.repo;

import com.es3.order.product.domain.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findAllByOrderByIdDesc(Pageable pageable);

    List<Product> findByIdLessThanOrderByIdDesc(Long cursor, Pageable pageable);
}
