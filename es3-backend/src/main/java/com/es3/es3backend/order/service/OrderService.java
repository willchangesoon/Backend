package com.es3.es3backend.order.service;

import com.es3.es3backend.config.exception.AuthException;
import com.es3.es3backend.config.exception.ErrorCode;
import com.es3.es3backend.config.exception.OrderException;
import com.es3.es3backend.config.exception.StoreException;
import com.es3.es3backend.order.domain.Order;
import com.es3.es3backend.order.domain.OrderStore;
import com.es3.es3backend.order.domain.repo.OrderRepository;
import com.es3.es3backend.order.dto.OrderDetailDto;
import com.es3.es3backend.order.dto.request.OrderItemForm;
import com.es3.es3backend.payment.domain.constants.PaymentMethod;
import com.es3.es3backend.store.domain.Store;
import com.es3.es3backend.store.domain.StoreRepository;
import com.es3.es3backend.user.domain.User;
import com.es3.es3backend.user.domain.UserRepository;
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
    private final StoreRepository storeRepository;
    private final UserRepository userRepository;

    public long creatOrder(User user,  List<Long> storeIds, List<OrderItemForm> orderItemFormList, PaymentMethod paymentMethod) {
        Order order = Order.createOrder(user);
        orderRepository.save(order);
        Map<Long, List<OrderItemForm>> groupedByShop = orderItemFormList.stream()
                .collect(Collectors.groupingBy(OrderItemForm::shopId));

        for (Long storeId : storeIds) {
            Store store = storeRepository.findById(storeId)
                    .orElseThrow(() -> new StoreException(ErrorCode.STORE_NOT_FOUND));
            OrderStore orderStore = order.addOrderStore(store);
            groupedByShop.get(storeId).forEach((items) -> {
                orderStore.addOrderItem(items.quantity(), items.unitPrice());
            });
        }

        order.initPayment(paymentMethod);
        return order.getId();
    }

    public OrderDetailDto completePayment(Long orderId, boolean success) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderException(ErrorCode.ORDER_NOT_FOUND));
        order.completePayment(success);
        //TODO product 수량 감소
        return OrderDetailDto.fromEntity(order);
    }

    private User getUser(User user) {
        return userRepository.findById(user.getId()).orElseThrow(() -> new AuthException(ErrorCode.USER_NOT_FOUND));
    }
}
