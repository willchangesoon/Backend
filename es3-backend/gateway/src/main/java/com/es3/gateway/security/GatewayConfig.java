//package com.es3.gateway.security;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.cloud.gateway.route.RouteLocator;
//import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//@RequiredArgsConstructor
//public class GatewayConfig {
//
//    private final JwtAuthFilter jwtAuthFilter;
//
//    @Bean
//    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
//        return builder.routes()
//                .route("auth_route", r -> r.path("/oauth/**") // ✅ "/oauth/**" 경로는 필터 적용 X
//                        .uri("http://localhost:8081"))
//                .route("user-api", r -> r.path("/users/**", "/users")
//                        .filters(f -> f.filter(jwtAuthFilter.apply(new JwtAuthFilter.Config()))) // ✅ JWT 필터 적용
//                        .uri("http://localhost:8081"))
//                .route("order-api", r -> r.path("/order/**")
//                        .filters(f -> f.filter(jwtAuthFilter.apply(new JwtAuthFilter.Config()))) // ✅ JWT 필터 적용
//                        .uri("http://localhost:8082"))
//                .build();
//    }
//
//}
