package com.es3.order.cart.controller;

import com.es3.order.cart.dto.AddToCartRequest;
import com.es3.order.cart.dto.CartItemResponse;
import com.es3.order.cart.dto.UpdateCartItemQuantityRequest;
import com.es3.order.cart.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/carts")
public class CartController {

    private final CartService cartService;

    @PostMapping
    public ResponseEntity<Void> addToCart(
            @RequestHeader("X-User-Id") Long userId,
            @RequestBody AddToCartRequest request
    ) {
        cartService.addToCart(userId, request.skuId(), request.quantity());
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<CartItemResponse>> getCartItems(
            @RequestHeader("X-User-Id") Long userId
    ) {
        return ResponseEntity.ok(cartService.getUserCartItems(userId));
    }

    @PatchMapping("/{cartItemId}")
    public ResponseEntity<Void> updateQuantity(
            @PathVariable Long cartItemId,
            @RequestBody UpdateCartItemQuantityRequest request
    ) {
        cartService.updateCartItem(cartItemId, request.quantity(), request.skuId());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{cartItemId}")
    public ResponseEntity<Void> deleteCartItem(@PathVariable Long cartItemId) {
        cartService.deleteCartItem(cartItemId);
        return ResponseEntity.noContent().build();
    }
}
