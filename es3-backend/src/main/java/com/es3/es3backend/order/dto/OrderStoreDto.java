package com.es3.es3backend.order.dto;

import com.es3.es3backend.order.domain.OrderStore;
import com.es3.es3backend.order.domain.constants.OrderStoreStatus;
import com.es3.es3backend.store.domain.Store;
import lombok.Builder;

import java.math.BigDecimal;
import java.util.List;

@Builder
public record OrderStoreDto (
        Long id,
        Long storeId,
        String storeName,
        OrderStoreStatus status,
        BigDecimal subTotalPrice,
        List<OrderItemDto> orderItemList
){

    public static OrderStoreDto fromEntity(OrderStore orderStore) {
        Store store = orderStore.getStore();
        return OrderStoreDto.builder()
                .id(orderStore.getId())
                .storeId(store.getId())
                .storeName(store.getName())
                .status(orderStore.getOrderStoreStatus())
                .subTotalPrice(orderStore.calculateSubTotal())
                .orderItemList(orderStore.getOrderItems().stream().map(OrderItemDto::fromEntity).toList())
                .build();
    }
}
