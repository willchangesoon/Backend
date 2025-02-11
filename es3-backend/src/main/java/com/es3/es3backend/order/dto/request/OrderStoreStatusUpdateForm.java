package com.es3.es3backend.order.dto.request;

import com.es3.es3backend.order.domain.constants.OrderStoreStatus;

public record OrderStoreStatusUpdateForm (
        OrderStoreStatus orderStoreStatus
){
}
