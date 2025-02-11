package com.es3.es3backend.order.service;

import com.es3.es3backend.config.exception.AuthException;
import com.es3.es3backend.config.exception.ErrorCode;
import com.es3.es3backend.config.exception.StoreException;
import com.es3.es3backend.order.domain.Order;
import com.es3.es3backend.order.domain.OrderItem;
import com.es3.es3backend.order.domain.OrderStore;
import com.es3.es3backend.order.domain.constants.OrderStatus;
import com.es3.es3backend.order.domain.constants.OrderStoreStatus;
import com.es3.es3backend.order.domain.repo.OrderItemRepository;
import com.es3.es3backend.order.domain.repo.OrderRepository;
import com.es3.es3backend.order.domain.repo.OrderStoreRepository;
import com.es3.es3backend.order.dto.request.OrderForm;
import com.es3.es3backend.order.dto.request.OrderItemForm;
import com.es3.es3backend.store.domain.StoreRepository;
import com.es3.es3backend.user.domain.User;
import com.es3.es3backend.user.domain.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class OrderCreateService {
    private final OrderRepository orderRepository;
    private final StoreRepository storeRepository;
    private final OrderStoreRepository orderStoreRepository;
    private final OrderItemRepository orderItemRepository;
    private final UserRepository userRepository;

    public long creatOrder(User user, OrderForm orderForm) {
        user = this.getUser(user);

        Map<Long, BigDecimal> priceByStore = calculateTotalPriceByShop(orderForm.items());
        BigDecimal totalPrice = priceByStore.values().stream().reduce(BigDecimal.ZERO, BigDecimal::add);
        List<OrderStore> orderStoreList = new ArrayList<>();
        List<OrderItem> orderItemList = new ArrayList<>();

        //order 저장
        Order order = orderRepository.save(Order.builder()
                .orderStatus(OrderStatus.PENDING)
                .totalPrice(totalPrice)
                .user(user)
                .build());
        //order store 저장
        for (Map.Entry<Long, BigDecimal> entry : priceByStore.entrySet()) {
            OrderStore orderStore = new OrderStore(
                    storeRepository.findById(entry.getKey()).orElseThrow(() -> new StoreException(ErrorCode.STORE_NOT_FOUND)),
                    order,
                    OrderStoreStatus.PENDING,
                    entry.getValue());
            orderStoreList.add(orderStore);
        }
        orderStoreRepository.saveAll(orderStoreList);
        //order items 저장
        for (OrderItemForm orderItemForm : orderForm.items()) {
            OrderStore store = orderStoreList.stream()
                    .filter(s -> s.getStore().getId().equals(orderItemForm.shopId())).findAny()
                    .orElseThrow(() -> new StoreException(ErrorCode.STORE_NOT_FOUND));
            OrderItem orderItem = new OrderItem(store, orderItemForm.quantity(), orderItemForm.unitPrice());
            orderItemList.add(orderItem);
        }
        orderItemRepository.saveAll(orderItemList);
        return order.getId();
    }

    private Map<Long, BigDecimal> calculateTotalPriceByShop(List<OrderItemForm> orderItems) {
        return orderItems.stream()
                .collect(Collectors.groupingBy(
                        OrderItemForm::shopId,  // shopId 기준으로 그룹화
                        Collectors.mapping(
                                item -> item.unitPrice().multiply(BigDecimal.valueOf(item.quantity())), // unitPrice * quantity 계산
                                Collectors.reducing(BigDecimal.ZERO, BigDecimal::add) // 합산
                        )
                ));
    }

    private User getUser(User user) {
        return userRepository.findById(user.getId()).orElseThrow(() -> new AuthException(ErrorCode.USER_NOT_FOUND));
    }
}
