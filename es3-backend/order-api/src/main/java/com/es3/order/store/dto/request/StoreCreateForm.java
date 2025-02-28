package com.es3.order.store.dto.request;

import lombok.Builder;

public record StoreCreateForm (
        String name,
        String logoImg,
        String description,
        String contactNumber
) {

    @Builder
    public StoreCreateForm {
    }
}
