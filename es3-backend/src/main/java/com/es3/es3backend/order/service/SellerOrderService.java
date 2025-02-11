package com.es3.es3backend.order.service;

import com.es3.es3backend.config.exception.AuthException;
import com.es3.es3backend.config.exception.ErrorCode;
import com.es3.es3backend.config.exception.OrderException;
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
        if (seller == null) {
            throw new AuthException(ErrorCode.NOT_AUTHORIZED);
        }
        return orderStoreRepository.findByStoreId(seller.getStore().getId()).stream().map(OrderStoreDto::fromEntity).toList();
    }

    public OrderStoreDto getOrderById(Seller seller, Long orderStoreId) {
        if (seller == null) {
            throw new AuthException(ErrorCode.NOT_AUTHORIZED);
        }
        return OrderStoreDto.fromEntity(orderStoreRepository.findByStoreIdAndId(seller.getStore().getId(), orderStoreId)
                .orElseThrow(() -> new OrderException(ErrorCode.ORDER_NOT_FOUND)));

    }
}
