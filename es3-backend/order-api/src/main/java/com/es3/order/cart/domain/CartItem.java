package com.es3.order.cart.domain;

import com.es3.order.common.entity.BaseEntity;
import com.es3.order.product.domain.ProductSKU;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "tb_cart_items")
public class CartItem extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sku_id")
    private ProductSKU sku;

    @Column(name = "quantity", nullable = false)
    private int quantity;

    public static CartItem create(Long userId, ProductSKU sku, int quantity) {
        return new CartItem(null, userId, sku, quantity);
    }

    public void changeQuantity(int newQuantity) {
        this.quantity = newQuantity;
    }
}

