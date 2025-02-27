package com.es3.order.store;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum StoreStatus {
    ACTIVATE, DEACTIVATE, BLOCK;
}
