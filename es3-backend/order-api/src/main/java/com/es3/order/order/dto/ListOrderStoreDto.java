package com.es3.order.order.dto;

import com.es3.order.order.domain.OrderStore;
import com.es3.order.order.domain.constants.OrderStoreStatus;
import com.es3.order.store.domain.Store;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record ListOrderStoreDto (
        Long id,
        Long storeId,
        String storeName,
        OrderStoreStatus status,
        BigDecimal subTotalPrice,
        int productSize,
        String coverImgUrl,
        String productTitle
){

    public static ListOrderStoreDto fromEntity(OrderStore orderStore) {
        Store store = orderStore.getStore();
        return ListOrderStoreDto.builder()
                .id(orderStore.getId())
                .storeId(store.getId())
                .storeName(store.getName())
                .status(orderStore.getOrderStoreStatus())
                .subTotalPrice(orderStore.calculateSubTotal())
                .productSize(orderStore.getOrderItems().size())
//                .coverImgUrl(orderStore.getOrderItems().get(0).getProduct()) TODO product 연결
                .build();
    }
}
