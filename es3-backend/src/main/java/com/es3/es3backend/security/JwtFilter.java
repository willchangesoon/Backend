package com.es3.es3backend.security;


import com.es3.es3backend.config.exception.AuthException;
import com.es3.es3backend.seller.service.SellerJoinService;
import com.es3.es3backend.user.service.AuthService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

	private final JwtUtil jwtUtil;
	private final AuthService authService;
	private final SellerJoinService sellerJoinService;
	final String PREFIX = "Bearer ";

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
		FilterChain filterChain) throws ServletException, IOException {
		String token = request.getHeader("Authorization");
		if (token != null && token.startsWith(PREFIX)) {
			token = token.substring(PREFIX.length());
			try {
				String email = jwtUtil.getEmail(token);
				UserDetails user = authService.loadUserByUsername(email);
				UsernamePasswordAuthenticationToken authentication =
					new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
				SecurityContextHolder.getContext().setAuthentication(authentication);
			} catch (AuthException authException) {
				String email = jwtUtil.getEmail(token);
				UserDetails user = sellerJoinService.loadUserByUsername(email);
				UsernamePasswordAuthenticationToken authentication =
					new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
				SecurityContextHolder.getContext().setAuthentication(authentication);
			} catch (Exception e) {
				log.debug(e.getMessage());
				throw e;
			}
		}
		filterChain.doFilter(request, response);
	}

	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) {
		return request.getRequestURI().startsWith("/oauth");
	}
}

