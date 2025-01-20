package com.es3.es3backend.user.service;

import com.es3.es3backend.config.exception.AuthException;
import com.es3.es3backend.config.exception.ErrorCode;
import com.es3.es3backend.security.EncryptionUtil;
import com.es3.es3backend.user.domain.User;
import com.es3.es3backend.user.domain.UserRepository;
import com.es3.es3backend.user.dto.UserDto;
import com.es3.es3backend.user.dto.request.SignInRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
@Transactional
public class AuthService implements UserDetailsService {
    private final UserRepository userRepository;

    public UserDto signUp(SignInRequest request) throws Exception {
        User user = userRepository.save(User.builder()
                        .email(request.email())
                        .name(request.name())
                        .address(request.address())
                        .mobile(request.mobile())
                        .profileImage(request.profile_img())
                        .password(EncryptionUtil.encrypt(request.password()))
                .build());
        return UserDto.fromEntity(user);
    }

    public UserDto login(String email, String password) throws Exception {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new AuthException(ErrorCode.USER_NOT_FOUND));
        UserDto userDto = UserDto.fromEntity(user);
        if (!userDto.verifyPassword(password)) {
            throw new AuthException(ErrorCode.INVALID_CREDENTIAL);
        }
        return userDto;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username).orElseThrow(() -> new AuthException(ErrorCode.USER_NOT_FOUND));
    }
}
