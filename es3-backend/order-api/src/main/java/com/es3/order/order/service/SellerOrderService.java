package com.es3.order.order.service;

import com.es3.order.config.exception.ErrorCode;
import com.es3.order.config.exception.OrderException;
import com.es3.order.config.exception.StoreException;
import com.es3.order.order.domain.OrderStore;
import com.es3.order.order.domain.repo.OrderStoreRepository;
import com.es3.order.order.dto.OrderStoreDto;
import com.es3.order.store.domain.Store;
import com.es3.order.store.domain.StoreRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class SellerOrderService {
    private final OrderStoreRepository orderStoreRepository;
    private final StoreRepository storeRepository;

    public List<OrderStoreDto> getOrderStoreList(String sellerId) {
        Store store = storeRepository.findBySellerId(Long.valueOf(sellerId))
                .orElseThrow(() -> new StoreException(ErrorCode.STORE_NOT_FOUND));
        return orderStoreRepository.findByStoreId(store.getId()).stream().map(OrderStoreDto::fromEntity).toList();
    }

    public OrderStoreDto getOrderById(String sellerId, Long orderStoreId) {
        return OrderStoreDto.fromEntity(orderStoreRepository.findById(orderStoreId)
                .orElseThrow(() -> new OrderException(ErrorCode.ORDER_NOT_FOUND)));
    }

    public void prepareShipment(String sellerId, Long orderStoreId) {
        OrderStore orderStore = findOrderStoreById(orderStoreId);
        orderStore.prepareShipment();
    }

    public void shipping(String sellerId, Long orderStoreId) {
        OrderStore orderStore = findOrderStoreById(orderStoreId);
        orderStore.shipping();
    }

    private OrderStore findOrderStoreById(Long orderStoreId) {
        return orderStoreRepository.findById(orderStoreId)
                .orElseThrow(() -> new OrderException(ErrorCode.ORDER_STORE_NOT_FOUND));
    }
}
