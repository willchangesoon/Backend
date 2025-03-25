package com.es3.order.product.service;

import com.es3.order.common.pagination.CursorPageResponse;
import com.es3.order.product.domain.Product;
import com.es3.order.product.domain.repo.ProductRepository;
import com.es3.order.product.dto.ProductResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductCommonService {
    private final ProductRepository productRepository;

    public List<ProductResponse> getAllProducts() {
        return productRepository.findAll().stream().map(ProductResponse::fromEntity).toList();
    }

    public CursorPageResponse<ProductResponse> getProductsByCursor(Long cursor, int pageSize) {
        Pageable pageable = PageRequest.of(0, pageSize);

        List<Product> products = (cursor == null)
                ? productRepository.findAllByOrderByIdDesc(pageable)
                : productRepository.findByIdLessThanOrderByIdDesc(cursor, pageable);

        List<ProductResponse> responses = products.stream()
                .map(ProductResponse::fromEntity)
                .toList();

        return CursorPageResponse.of(responses, pageSize);
    }
}
