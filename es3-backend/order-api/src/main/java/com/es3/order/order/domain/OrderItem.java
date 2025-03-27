package com.es3.order.order.domain;

import com.es3.order.common.entity.BaseEntity;
import com.es3.order.config.exception.ErrorCode;
import com.es3.order.config.exception.OrderException;
import com.es3.order.product.domain.ProductSKU;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name = "tb_order_items")
public class OrderItem extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sku_id")
    private ProductSKU sku;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_store_id")
    private OrderStore orderStore;

    private int quantity;

    private BigDecimal unitPrice;

    private boolean isCancelled = false;

    public static OrderItem createOrderItem(ProductSKU sku, OrderStore orderStore, int quantity, BigDecimal unitPrice) {
        return new OrderItem(null, sku, orderStore, quantity, unitPrice, false);
    }

    public BigDecimal calculateTotal() {
        return unitPrice.multiply(BigDecimal.valueOf(quantity));
    }

    public void cancel() {
        if (isCancelled) {
            throw new OrderException(ErrorCode.ALREADY_CANCELLED_ITEM);
        }
        this.isCancelled = true;
    }

    public Long getSkuId() {
        return sku.getId();
    }
}
