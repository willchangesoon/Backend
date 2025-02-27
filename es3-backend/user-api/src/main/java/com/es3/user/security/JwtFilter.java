package com.es3.user.security;

import com.sun.security.auth.UserPrincipal;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class JwtFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String id = request.getHeader("X-User-Id");
        String username = request.getHeader("X-User-Mail");
        String role = request.getHeader("X-User-Role");

        if (id != null && username != null && role != null) {
            UserPrincipal userPrincipal = new UserPrincipal(id);

            GrantedAuthority authority = new SimpleGrantedAuthority(role);
            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    userPrincipal, null, List.of(authority)
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } else {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }
        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        List<String> excludePaths = Arrays.asList("/user-v1/oauth");

        String requestUri = request.getRequestURI();

        return excludePaths.stream().anyMatch(requestUri::startsWith);
    }
}
