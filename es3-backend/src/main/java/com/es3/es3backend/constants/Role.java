package com.es3.es3backend.constants;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {
    USER ("ROLE_USER"),
    SELLER("ROLE_SELLER,ROLE_USER"),
    ADMIN ("ROLE_ADMIN,ROLE_SELLER,ROLE_USER");

    private final String roles;
}
