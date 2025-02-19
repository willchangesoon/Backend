package com.es3.es3backend.order.service;

import com.es3.es3backend.config.exception.AuthException;
import com.es3.es3backend.config.exception.ErrorCode;
import com.es3.es3backend.config.exception.OrderException;
import com.es3.es3backend.order.domain.OrderStore;
import com.es3.es3backend.order.domain.repo.OrderStoreRepository;
import com.es3.es3backend.order.dto.OrderStoreDto;
import com.es3.es3backend.seller.domain.Seller;
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

    public List<OrderStoreDto> getOrderStoreList(Seller seller) {
        validateSeller(seller);
        return orderStoreRepository.findByStoreId(seller.getStore().getId()).stream().map(OrderStoreDto::fromEntity).toList();
    }

    public OrderStoreDto getOrderById(Seller seller, Long orderStoreId) {
        validateSeller(seller);
        return OrderStoreDto.fromEntity(orderStoreRepository.findByStoreIdAndId(seller.getStore().getId(), orderStoreId)
                .orElseThrow(() -> new OrderException(ErrorCode.ORDER_NOT_FOUND)));
    }

    public void prepareShipment(Seller seller, Long orderStoreId) {
        validateSeller(seller);
        OrderStore orderStore = findOrderStoreById(orderStoreId);
        orderStore.prepareShipment();
    }

    public void shipping(Seller seller, Long orderStoreId) {
        OrderStore orderStore = findOrderStoreById(orderStoreId);
        orderStore.shipping();
    }

    private static void validateSeller(Seller seller) {
        if (seller == null) {
            throw new AuthException(ErrorCode.NOT_AUTHORIZED);
        }
    }

    private OrderStore findOrderStoreById(Long orderStoreId) {
        return orderStoreRepository.findById(orderStoreId)
                .orElseThrow(() -> new OrderException(ErrorCode.ORDER_STORE_NOT_FOUND));
    }
}
