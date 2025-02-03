package com.es3.es3backend.store.dto.request;

import lombok.Builder;

public record StoreCreateForm (
        String name,
        String logoImg,
        String description,
        String address,
        String contactNumber
) {

    @Builder
    public StoreCreateForm {
    }


}
