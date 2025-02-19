package com.es3.es3backend.order.domain;

import com.es3.es3backend.common.entity.BaseEntity;
import com.es3.es3backend.config.exception.ErrorCode;
import com.es3.es3backend.config.exception.OrderException;
import com.es3.es3backend.order.domain.constants.OrderStoreStatus;
import com.es3.es3backend.store.domain.Store;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "tb_order_store")
public class OrderStore extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "store_id")
    private Store store;

    @OneToMany(mappedBy = "orderStore", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private OrderStoreStatus orderStoreStatus;

    public static OrderStore createOrderStore(Store store, Order order) {
        return new OrderStore(null, store, new ArrayList<>(), order, OrderStoreStatus.PENDING);
    }

    public OrderItem addOrderItem(int quantity, BigDecimal unitPrice) {
        OrderItem orderItem = OrderItem.createOrderItem(this, quantity, unitPrice);
        orderItems.add(orderItem);
        return orderItem;
    }

    public BigDecimal calculateSubTotal() {
        return this.orderItems.stream().map(item -> item.getUnitPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public void completePayment(boolean success) {
        if (!orderStoreStatus.equals(OrderStoreStatus.PENDING)) {
            throw new OrderException(ErrorCode.ILLEGAL_ORDER_STATE);
        }
        if (success) {
            orderStoreStatus = OrderStoreStatus.ORDER_RECEIVED;
        }
    }

    public void prepareShipment() {
        if(!orderStoreStatus.equals(OrderStoreStatus.ORDER_RECEIVED)) {
            throw new OrderException(ErrorCode.ILLEGAL_ORDER_STATE);
        }
        orderStoreStatus = OrderStoreStatus.PREPARING_SHIPMENT;
    }

    public void shipping() {
        if(!orderStoreStatus.equals(OrderStoreStatus.PREPARING_SHIPMENT)) {
            throw new OrderException(ErrorCode.ILLEGAL_ORDER_STATE);
        }
        orderStoreStatus = OrderStoreStatus.OUT_FOR_DELIVERY;
    }

    public void delivered() {
        if(!orderStoreStatus.equals(OrderStoreStatus.OUT_FOR_DELIVERY)) {
            throw new OrderException(ErrorCode.ILLEGAL_ORDER_STATE);
        }
        orderStoreStatus = OrderStoreStatus.DELIVERED;
    }

    public void complete() {
        if (!orderStoreStatus.equals(OrderStoreStatus.DELIVERED)) { //   배달 완료 상태에서만 주문 완료 가능
            throw new OrderException(ErrorCode.ILLEGAL_ORDER_STATE);
        }
        orderStoreStatus = OrderStoreStatus.ORDER_COMPLETED;
    }

    public BigDecimal cancelItems(List<Long> itemIds) {
        BigDecimal cancelAmount = BigDecimal.ZERO;

        if(orderStoreStatus.equals(OrderStoreStatus.ORDER_COMPLETED)) {
            throw new OrderException(ErrorCode.ILLEGAL_ORDER_STATE);
        }

        for (OrderItem item : orderItems) {
            if (itemIds.contains(item.getId()) && !item.isCancelled()) {
                cancelAmount = cancelAmount.add(item.calculateTotal());
                item.cancel();
            }
        }

        if (isAllItemsCancelled()) {
            this.orderStoreStatus = OrderStoreStatus.CANCELLED;
        }

        return cancelAmount;
    }

    public boolean isAllItemsCancelled() {
        return orderItems.stream().allMatch(OrderItem::isCancelled);
    }
}
