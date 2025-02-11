package com.es3.es3backend.order.service;

import com.es3.es3backend.config.exception.ErrorCode;
import com.es3.es3backend.config.exception.OrderException;
import com.es3.es3backend.order.domain.Order;
import com.es3.es3backend.order.domain.repo.OrderRepository;
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

    public OrderDetailDto getOrderDetailByOrderId(User user, Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new OrderException(ErrorCode.ORDER_NOT_FOUND));
        return OrderDetailDto.fromEntity(order);
    }

    public List<OrderListDto> getOrderListByUser(User user) {
        List<Order> orderList = orderRepository.findByUserId(user.getId());
        return orderList.stream().map(OrderListDto::fromEntity).toList();
    }
}
