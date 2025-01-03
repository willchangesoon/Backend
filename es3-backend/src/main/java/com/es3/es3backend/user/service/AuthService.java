package com.es3.es3backend.user.service;

import com.es3.es3backend.security.EncryptionUtil;
import com.es3.es3backend.user.domain.User;
import com.es3.es3backend.user.domain.UserRepository;
import com.es3.es3backend.user.dto.UserDto;
import com.es3.es3backend.user.dto.request.SignInRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
@Transactional
public class AuthService{
    private final UserRepository userRepository;

    public UserDto signUp(SignInRequest request) throws Exception {
        User user = userRepository.save(User.builder()
                        .email(request.email())
                        .name(request.name())
                        .address(request.address())
                        .mobile(request.mobile())
                        .profile_img(request.profile_img())
                        .password(EncryptionUtil.encrypt(request.password()))
                .build());
        return UserDto.fromEntity(user);
    }

    public UserDto login(String email, String password) throws Exception {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("user not found"));
        UserDto userDto = UserDto.fromEntity(user);
        if (!userDto.verifyPassword(password)) {
            throw new RuntimeException("Invalid credentials");
        }
        return userDto;
    }
}
