package com.es3.es3backend.user.service;

import com.es3.es3backend.config.exception.CustomException;
import com.es3.es3backend.config.exception.ErrorCode;
import com.es3.es3backend.user.domain.User;
import com.es3.es3backend.user.domain.UserRepository;
import com.es3.es3backend.user.dto.request.UpdateRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class UserUpdateService {
    private static final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
    private static final String PASSWORD_REGEX = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";

    private final UserRepository userRepository;

    @Transactional
    public void updateEmail(User user, UpdateRequest.Email email) {
        user = userRepository.findByEmail(user.getEmail())
                .orElseThrow(() -> new CustomException(ErrorCode.INVALID_EMAIL));
        if (userRepository.existsByEmail(email.getEmail())){
            throw new CustomException(ErrorCode.REGISTERED_EMAIL);
        }
        if(!Pattern.matches(EMAIL_REGEX, email.getEmail())){
            throw new CustomException(ErrorCode.INVALID_EMAIL);
        }
        user.updateEmail(email.getEmail());
    }

    public void updateName(User user, UpdateRequest.Name name) {
        user = userRepository.findByEmail(user.getEmail())
                .orElseThrow(() -> new CustomException(ErrorCode.INVALID_EMAIL));
        user.updateName(name.getName());
    }

    public void updatePassword(User user, UpdateRequest.Password password) {
        user = userRepository.findByEmail(user.getEmail())
                .orElseThrow(() -> new CustomException(ErrorCode.INVALID_EMAIL));
        if (!Pattern.matches(PASSWORD_REGEX, password.getNewPassword())) {
            throw new CustomException(ErrorCode.INVALID_PASSWORD);
        }
        user.updatePassword(password.getOldPassword(), password.getNewPassword());
    }

    public void updateAddress(User user, UpdateRequest.Address address) {
        user = userRepository.findByEmail(user.getEmail())
                .orElseThrow(() -> new CustomException(ErrorCode.INVALID_EMAIL));
        user.updateAddress(address.getAddress());
    }

    public void updateMobile(User user, UpdateRequest.Mobile mobile) {
        user = userRepository.findByEmail(user.getEmail())
                .orElseThrow(() -> new CustomException(ErrorCode.INVALID_EMAIL));
        user.updateMobile(mobile.getMobile());
    }

    public void updateProfileImage(User user, UpdateRequest.ProfileImage profileImage) {
        user = userRepository.findByEmail(user.getEmail())
                .orElseThrow(() -> new CustomException(ErrorCode.INVALID_EMAIL));
        user.updateProfileImage(profileImage.getProfile_image());
    }



}
