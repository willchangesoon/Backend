package com.es3.order.product.domain;

import com.es3.order.config.exception.ErrorCode;
import com.es3.order.config.exception.ProductException;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "tb_product_skus")
public class ProductSKU {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToMany
    @JoinTable(
            name = "tb_product_sku_options",
            joinColumns = @JoinColumn(name = "sku_id"),
            inverseJoinColumns = @JoinColumn(name = "option_id")
    )
    private List<ProductOption> options = new ArrayList<>();

    private int quantity;

    private BigDecimal additionalPrice;

    public void increaseStock(int stockQuantity) {
        validateStockQuantity(stockQuantity);
        if (this.quantity + stockQuantity < 0) {
            throw new ProductException(ErrorCode.STOCK_QUANTITY_ARITHMETIC);
        }
        this.quantity += stockQuantity;
    }

    public void decreaseStock(int stockQuantity) {
        validateStockQuantity(stockQuantity);
        if (this.quantity < stockQuantity) {
            throw new ProductException(ErrorCode.INSUFFICIENT_STOCK);
        }
        this.quantity -= stockQuantity;
    }

    private static void validateStockQuantity(int stockQuantity) {
        if (stockQuantity <= 0) {
            throw new ProductException(ErrorCode.INVALID_STOCK_QUANTITY);
        }
    }
}
