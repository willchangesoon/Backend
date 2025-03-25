package com.es3.gateway.security;

import com.es3.gateway.domain.common.Role;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

@Component
public class JwtAuthFilter extends AbstractGatewayFilterFactory<JwtAuthFilter.Config> {

    private final JwtProvider jwtProvider;

    public JwtAuthFilter(JwtProvider jwtProvider) {
        super(Config.class);
        this.jwtProvider = jwtProvider;
    }

    public static class Config {
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            String token = extractToken(exchange.getRequest().getHeaders().getFirst("Authorization"));
            if (token != null && jwtProvider.validateToken(token)) {
                String userId = jwtProvider.getUserId(token); // JWT subject = userId
                Role role = jwtProvider.getRoles(token); // role 정보 가져오기
                String mail = jwtProvider.getEmail(token);

                // 새 요청에 사용자 정보 추가
                ServerHttpRequest modifiedRequest = request.mutate()
                        .header("X-User-Id", userId)
                        .header("X-User-Mail", mail)
                        .header("X-User-Role", role.name())
                        .build();

                return chain.filter(exchange.mutate().request(modifiedRequest).build());
            }
            return chain.filter(exchange);
        };
    }

    private String extractToken(String authHeader) {
        return (authHeader != null && authHeader.startsWith("Bearer ")) ? authHeader.substring(7) : null;
    }
}
