package com.es3.order.cart.service;

import com.es3.order.cart.domain.CartItem;
import com.es3.order.cart.domain.repo.CartItemRepository;
import com.es3.order.config.exception.CartException;
import com.es3.order.config.exception.ErrorCode;
import com.es3.order.config.exception.ProductException;
import com.es3.order.product.domain.ProductSKU;
import com.es3.order.product.domain.repo.ProductSKURepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CartService {
    private final CartItemRepository cartItemRepository;
    private final ProductSKURepository productSKURepository;

    public CartItem addToCart(Long userId, Long skuId, int quantity) {
        ProductSKU sku = productSKURepository.findById(skuId)
                .orElseThrow(() -> new ProductException(ErrorCode.SKU_NOT_FOUND));

        // 이미 해당 SKU가 장바구니에 있으면 수량 증가
        return cartItemRepository.findByUserIdAndSkuId(userId, skuId)
                .map(item -> {
                    item.changeQuantity(item.getQuantity() + quantity);
                    return item;
                })
                .orElseGet(() -> cartItemRepository.save(CartItem.create(userId, sku, quantity)));
    }

    /**
     * 유저 장바구니 조회
     */
    @Transactional(readOnly = true)
    public List<CartItem> getUserCartItems(Long userId) {
        return cartItemRepository.findByUserId(userId);
    }

    /**
     * 수량 변경
     */
    public void updateQuantity(Long cartItemId, int newQuantity) {
        CartItem item = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new CartException(ErrorCode.CART_ITEM_NOT_FOUND));
        item.changeQuantity(newQuantity);
    }

    /**
     * 장바구니 항목 삭제
     */
    public void deleteCartItem(Long cartItemId) {
        cartItemRepository.deleteById(cartItemId);
    }

}
