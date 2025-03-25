package com.es3.order.product.service;

import com.es3.order.category.domain.Category;
import com.es3.order.category.domain.CategoryRepository;
import com.es3.order.config.exception.ErrorCode;
import com.es3.order.config.exception.ProductException;
import com.es3.order.config.exception.StoreException;
import com.es3.order.product.domain.Product;
import com.es3.order.product.domain.ProductOption;
import com.es3.order.product.domain.repo.ProductOptionRepository;
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
    private final ProductOptionRepository optionRepository;
    private final StoreRepository storeRepository;
    private final CategoryRepository categoryRepository;

    @Transactional
    public void createProduct(String userId, ProductCreateForm productCreateForm) {
        Store store = getStoreBySeller(Long.parseLong(userId));
        Product product = Product.createProduct(productCreateForm, store);
        product.addProductOptions(productCreateForm.productOptionList());
        productRepository.save(product);
    }

    private Category getCategory(ProductCreateForm productCreateForm) {
        return categoryRepository.findById(productCreateForm.categoryId())
                .orElseThrow(() -> new RuntimeException("category not found"));
    }

    private Store getStoreBySeller(Long sellerId) {
        return storeRepository.findBySellerId(sellerId)
                .orElseThrow(() -> new StoreException(ErrorCode.STORE_NOT_FOUND));
    }

    public void decreaseStock(Long productOptionId, int quantity) {
        ProductOption productOption = optionRepository.findById(productOptionId)
                .orElseThrow(() -> new ProductException(ErrorCode.PRODUCT_NOT_FOUND));
        productOption.decreaseStock(quantity);
    }

    public void increaseStock(Long productOptionId, int quantity) {
        ProductOption productOption = optionRepository.findById(productOptionId)
                .orElseThrow(() -> new ProductException(ErrorCode.PRODUCT_NOT_FOUND));
        productOption.increaseStock(quantity);
    }
}
