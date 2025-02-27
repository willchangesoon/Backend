package com.es3.order.order.dto.request;


import com.es3.order.order.domain.constants.OrderStoreStatus;

public record OrderStoreStatusUpdateForm (
        OrderStoreStatus orderStoreStatus
){
}
