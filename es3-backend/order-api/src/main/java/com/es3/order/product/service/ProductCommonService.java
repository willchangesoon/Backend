package com.es3.order.product.service;

import com.es3.order.common.pagination.CursorPageResponse;
import com.es3.order.config.exception.ErrorCode;
import com.es3.order.config.exception.ProductException;
import com.es3.order.product.domain.Product;
import com.es3.order.product.domain.ProductOptionGroup;
import com.es3.order.product.domain.ProductSKU;
import com.es3.order.product.domain.repo.ProductOptionGroupRepository;
import com.es3.order.product.domain.repo.ProductRepository;
import com.es3.order.product.domain.repo.ProductSKURepository;
import com.es3.order.product.dto.response.ProductDetailResponse;
import com.es3.order.product.dto.response.ProductOptionGroupResponse;
import com.es3.order.product.dto.response.ProductResponse;
import com.es3.order.product.dto.response.ProductSKUResponse;
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
    private final ProductOptionGroupRepository productOptionGroupRepository;
    private final ProductSKURepository productSKURepository;

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

    public ProductDetailResponse getProductDetail(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductException(ErrorCode.PRODUCT_NOT_FOUND));
        product.getAdditionalImages().size();   //lazy 초기화용
        return ProductDetailResponse.fromEntity(product);
    }

    public List<ProductOptionGroupResponse> getProductOptions(Long productId) {
        List<ProductOptionGroup> productOptionGroup = productOptionGroupRepository.findAllByProductId(productId);
        return productOptionGroup.stream().map(ProductOptionGroupResponse::fromEntity).toList();
    }

    public List<ProductSKUResponse> getProductSkus(Long productId) {
        List<ProductSKU> productSKUResponses = productSKURepository.findAllByProductId(productId);
        return productSKUResponses.stream().map(ProductSKUResponse::fromEntity).toList();
    }
}
