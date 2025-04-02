package com.es3.order.product.domain.repo;

import com.es3.order.product.domain.Product;
import com.es3.order.product.dto.ProductSearchCond;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductQueryRepository {
    List<Product> searchProducts(ProductSearchCond cond, Pageable pageable);
}

