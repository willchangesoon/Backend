package com.es3.order.order.service;

import com.es3.order.config.exception.ErrorCode;
import com.es3.order.config.exception.OrderException;
import com.es3.order.order.domain.Order;
import com.es3.order.order.domain.OrderStore;
import com.es3.order.order.domain.repo.OrderRepository;
import com.es3.order.order.domain.repo.OrderStoreRepository;
import com.es3.order.order.dto.OrderDetailDto;
import com.es3.order.order.dto.OrderListDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class UserOrderService {
    private final OrderRepository orderRepository;
    private final OrderStoreRepository orderStoreRepository;

    public OrderDetailDto getOrderDetailByOrderId(String userId, Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new OrderException(ErrorCode.ORDER_NOT_FOUND));
        return OrderDetailDto.fromEntity(order);
    }

    public List<OrderListDto> getOrderListByUser(String userId) {
        List<Order> orderList = orderRepository.findByUserId(Long.valueOf(userId));
        return orderList.stream().map(OrderListDto::fromEntity).toList();
    }

    public void delivered(Long orderStoreId) {
        OrderStore orderStore = findOrderStoreById(orderStoreId);
        orderStore.delivered();
    }

    public void complete(Long orderStoreId) {
        OrderStore orderStore = findOrderStoreById(orderStoreId);
        orderStore.complete();
    }

    public OrderStore findOrderStoreById(Long orderStoreId) {
        return orderStoreRepository.findById(orderStoreId)
                .orElseThrow(() -> new OrderException(ErrorCode.ORDER_STORE_NOT_FOUND));
    }
}
