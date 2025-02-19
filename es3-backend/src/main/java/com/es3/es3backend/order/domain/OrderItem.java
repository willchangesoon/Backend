package com.es3.es3backend.order.domain;

import com.es3.es3backend.common.entity.BaseEntity;
import com.es3.es3backend.config.exception.ErrorCode;
import com.es3.es3backend.config.exception.OrderException;
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
    @Column(name = "id")
    private Long id;

//    @OneToOne
//    @JoinColumn(name = "product_id")
//    private Product product;

    @ManyToOne
    @JoinColumn(name = "order_store_id")
    private OrderStore orderStore;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "unit_price")
    private BigDecimal unitPrice;

    @Column(name = "is_cancelled")
    private boolean isCancelled = false;

    public static OrderItem createOrderItem(OrderStore orderStore, int quantity, BigDecimal unitPrice) {
        return new OrderItem(null, orderStore, quantity, unitPrice, false);
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

    public boolean isCancelled() {
        return isCancelled;
    }

}
