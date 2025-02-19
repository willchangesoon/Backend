package com.es3.es3backend.order.service;

import com.es3.es3backend.config.exception.ErrorCode;
import com.es3.es3backend.config.exception.OrderException;
import com.es3.es3backend.order.domain.Order;
import com.es3.es3backend.order.domain.OrderStore;
import com.es3.es3backend.order.domain.repo.OrderRepository;
import com.es3.es3backend.order.domain.repo.OrderStoreRepository;
import com.es3.es3backend.order.dto.OrderDetailDto;
import com.es3.es3backend.order.dto.OrderListDto;
import com.es3.es3backend.user.domain.User;
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

    public OrderDetailDto getOrderDetailByOrderId(User user, Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new OrderException(ErrorCode.ORDER_NOT_FOUND));
        return OrderDetailDto.fromEntity(order);
    }

    public List<OrderListDto> getOrderListByUser(User user) {
        List<Order> orderList = orderRepository.findByUserId(user.getId());
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
