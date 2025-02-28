package com.es3.order.product.service;

import com.es3.order.category.domain.Category;
import com.es3.order.category.domain.CategoryRepository;
import com.es3.order.config.exception.ErrorCode;
import com.es3.order.config.exception.StoreException;
import com.es3.order.product.domain.Product;
import com.es3.order.product.domain.repo.ProductRepository;
import com.es3.order.product.dto.ProductCreateForm;
import com.es3.order.store.domain.Store;
import com.es3.order.store.domain.StoreRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final StoreRepository storeRepository;
    private final CategoryRepository categoryRepository;

    @Transactional
    public void createProduct(String userId, ProductCreateForm productCreateForm) {
        Store store = getStoreBySeller(productCreateForm.storeId(), Long.parseLong(userId));
        Category category = getCategory(productCreateForm);
        Product product = Product.createProduct(productCreateForm, store, category);
        product.addProductOptions(productCreateForm.productOptionList());
        productRepository.save(product);
    }

    private Category getCategory(ProductCreateForm productCreateForm) {
        return categoryRepository.findById(productCreateForm.categoryId())
                .orElseThrow(() -> new RuntimeException("category not found"));
    }

    private Store getStoreBySeller(Long storeId, Long sellerId) {
        return storeRepository.findByIdAndSellerId(storeId, sellerId)
                .orElseThrow(() -> new StoreException(ErrorCode.STORE_NOT_FOUND));
    }
}
