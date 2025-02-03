package com.es3.es3backend.auth.service;

import com.es3.es3backend.config.exception.AuthException;
import com.es3.es3backend.config.exception.ErrorCode;
import com.es3.es3backend.seller.domain.SellerRepository;
import com.es3.es3backend.user.domain.User;
import com.es3.es3backend.user.domain.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
@Slf4j
@Transactional
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;
    private final SellerRepository sellerRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByEmail(username);
        if (user.isEmpty()) {
            return sellerRepository.findByEmail(username).orElseThrow(
                    () -> new AuthException(ErrorCode.USER_NOT_FOUND)
            );
        }
        return user.get();
    }
}
