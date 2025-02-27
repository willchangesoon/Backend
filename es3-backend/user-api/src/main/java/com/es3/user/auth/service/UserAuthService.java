package com.es3.user.auth.service;

import com.es3.user.auth.dto.user.request.UserSignUpForm;
import com.es3.user.auth.security.EncryptionUtil;
import com.es3.user.config.exception.AuthException;
import com.es3.user.config.exception.ErrorCode;
import com.es3.user.user.domain.User;
import com.es3.user.user.domain.UserRepository;
import com.es3.user.user.dto.UserDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
@Transactional
public class UserAuthService {
    private final UserRepository userRepository;
    private final ValidationUtil validationUtil;

    public UserDto signUp(UserSignUpForm request) throws Exception {
        validationUtil.checkEmail(request.email());
        validationUtil.checkPassword(request.password());
        if(userRepository.existsByEmail(request.email())) {
            throw new AuthException(ErrorCode.REGISTERED_EMAIL);
        }
        if(userRepository.existsByMobile(request.mobile())) {
            throw new AuthException(ErrorCode.REGISTERED_MOBILE);
        }
        User user = userRepository.save(User.builder()
                .email(request.email())
                .name(request.name())
                .address(request.address())
                .mobile(request.mobile())
                .profileImage(request.profileImg())
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
}
