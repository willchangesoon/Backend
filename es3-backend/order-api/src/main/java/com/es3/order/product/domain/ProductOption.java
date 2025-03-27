package com.es3.order.product.domain;
//
//import com.es3.order.common.entity.BaseEntity;
//import com.es3.order.config.exception.ErrorCode;
//import com.es3.order.config.exception.ProductException;
//import com.es3.order.product.dto.ProductOptionForm;
//import jakarta.persistence.*;
//import lombok.AccessLevel;
//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import org.springframework.data.jpa.domain.support.AuditingEntityListener;
//
//import java.math.BigDecimal;
//
//@Entity
//@Getter
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
//@AllArgsConstructor(access = AccessLevel.PRIVATE)
//@EntityListeners(AuditingEntityListener.class)
//@Table(name = "tb_product_options")
//public class ProductOption extends BaseEntity {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "id")
//    private Long id;
//
//    @ManyToOne
//    @JoinColumn(name = "product_id")
//    private Product product;
//
//    @Column(name = "name", nullable = false)
//    private String name;
//
//    @Column(name = "value", nullable = false)
//    private String value;
//
//    @Column(name = "quantity", nullable = false)
//    private int quantity;
//
//    @Column(name = "additional_price")
//    private BigDecimal additionalPrice;
//
//    public static ProductOption createOption(Product product, ProductOptionForm form) {
//        return new ProductOption(null, product, form.name(), form.value(), form.quantity(), form.additionalPrice());
//    }
//
//    public void increaseStock(int stockQuantity) {
//        validateStockQuantity(stockQuantity);
//        if (this.quantity + stockQuantity < 0) {
//            throw new ProductException(ErrorCode.STOCK_QUANTITY_ARITHMETIC);
//        }
//        this.quantity += stockQuantity;
//    }
//
//    public void decreaseStock(int stockQuantity) {
//        validateStockQuantity(stockQuantity);
//        if (this.quantity < stockQuantity) {
//            throw new ProductException(ErrorCode.INSUFFICIENT_STOCK);
//        }
//        this.quantity -= stockQuantity;
//    }
//
//    private static void validateStockQuantity(int stockQuantity) {
//        if (stockQuantity <= 0) {
//            throw new ProductException(ErrorCode.INVALID_STOCK_QUANTITY);
//        }
//    }
//}

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "tb_product_options")
public class ProductOption {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String value; // ex: black, M

    @ManyToOne
    @JoinColumn(name = "option_group_id")
    private ProductOptionGroup optionGroup;
}