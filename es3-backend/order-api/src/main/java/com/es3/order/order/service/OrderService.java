package com.es3.order.order.service;

import com.es3.order.config.exception.ErrorCode;
import com.es3.order.config.exception.OrderException;
import com.es3.order.config.exception.StoreException;
import com.es3.order.order.domain.Order;
import com.es3.order.order.domain.OrderItem;
import com.es3.order.order.domain.OrderStore;
import com.es3.order.order.domain.repo.OrderItemRepository;
import com.es3.order.order.domain.repo.OrderRepository;
import com.es3.order.order.dto.OrderDetailDto;
import com.es3.order.order.dto.request.OrderItemForm;
import com.es3.order.payment.domain.constants.PaymentMethod;
import com.es3.order.product.service.ProductService;
import com.es3.order.store.domain.Store;
import com.es3.order.store.domain.StoreRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final StoreRepository storeRepository;
    private final ProductService productService;

    public long creatOrder(String userId, List<Long> storeIds, List<OrderItemForm> orderItemFormList, PaymentMethod paymentMethod) {
        Order order = Order.createOrder(Long.valueOf(userId));
        orderRepository.save(order);
        Map<Long, List<OrderItemForm>> groupedByShop = orderItemFormList.stream()
                .collect(Collectors.groupingBy(OrderItemForm::shopId));

        for (Long storeId : storeIds) {
            Store store = storeRepository.findById(storeId)
                    .orElseThrow(() -> new StoreException(ErrorCode.STORE_NOT_FOUND));
            OrderStore orderStore = order.addOrderStore(store);
            groupedByShop.get(storeId).forEach((items) -> {
                orderStore.addOrderItem(items.productOptionId(), items.quantity(), items.unitPrice());
            });
        }

        order.initPayment(paymentMethod);
        return order.getId();
    }

    public OrderDetailDto completePayment(Long orderId, boolean success) {
        Order order = findOrderById(orderId);
        order.completePayment(success);
        decreaseStock(success, order);
        return OrderDetailDto.fromEntity(order);
    }

    private Order findOrderById(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderException(ErrorCode.ORDER_NOT_FOUND));
    }

    public OrderDetailDto cancelOrder(Long orderId, List<Long> orderItemIds) {
        Order order = findOrderById(orderId);

        Map<OrderStore, List<Long>> storeItemMap = orderItemIds.stream()
                .collect(Collectors.groupingBy(order::findOrderStoreByOrderItemId));

        storeItemMap.forEach(order::cancel);
        increaseStock(orderItemIds);
        return OrderDetailDto.fromEntity(order);
    }

    private void decreaseStock(boolean success, Order order) {
        if (success) {
            order.getOrderItems().forEach(orderItem ->
                    productService.decreaseStock(orderItem.getProductOptionId(), orderItem.getQuantity())
            );
        }
    }

    private void increaseStock(List<Long> orderItemIds) {
        List<OrderItem> orderItems = orderItemRepository.findAllById(orderItemIds);

        if (orderItems.size() != orderItemIds.size()) {
            throw new OrderException(ErrorCode.ORDER_ITEM_NOT_FOUND);
        }

        orderItems.forEach(item ->
                productService.increaseStock(item.getProductOptionId(), item.getQuantity())
        );
    }
}
