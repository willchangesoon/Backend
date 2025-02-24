package com.es3.gateway.domain.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@RequiredArgsConstructor
public enum Role {
    USER(List.of("ROLE_USER")),
    SELLER(List.of("ROLE_SELLER", "ROLE_USER")),
    ADMIN(List.of("ROLE_ADMIN", "ROLE_SELLER", "ROLE_USER"));

    private final List<String> roles;


    public List<String> getAuthorities() {
        return roles.stream()
                .map(role -> "ROLE_" + role.replace("ROLE_", ""))
                .collect(Collectors.toList());
    }
}
