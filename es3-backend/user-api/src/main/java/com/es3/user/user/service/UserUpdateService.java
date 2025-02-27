package com.es3.user.user.service;


import com.es3.user.auth.service.ValidationUtil;
import com.es3.user.config.exception.AuthException;
import com.es3.user.config.exception.ErrorCode;
import com.es3.user.user.domain.User;
import com.es3.user.user.domain.UserRepository;
import com.es3.user.user.dto.request.UpdateRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class UserUpdateService {
    private final UserRepository userRepository;
    private final ValidationUtil validationUtil;

    public User getUser(String userId) {
        return userRepository.findById(Long.valueOf(userId))
                .orElseThrow(() -> new AuthException(ErrorCode.USER_NOT_FOUND));
    }

    public void updateEmail(String userId, UpdateRequest.Email email) {
        User user = this.getUser(userId);
        if (userRepository.existsByEmail(email.getEmail())){
            throw new AuthException(ErrorCode.REGISTERED_EMAIL);
        }
        validationUtil.checkEmail(email.getEmail());
        user.updateEmail(email.getEmail());
    }

    public void updateName(String userId, UpdateRequest.Name name) {
        User user = this.getUser(userId);
        user.updateName(name.getName());
    }

    public void updatePassword(String userId, UpdateRequest.Password password) {
        User user = this.getUser(userId);
        validationUtil.checkPassword(password.getNewPassword());
        user.updatePassword(password.getOldPassword(), password.getNewPassword());
    }

    public void updateAddress(String userId, UpdateRequest.Address address) {
        User user = this.getUser(userId);
        user.updateAddress(address.getAddress());
    }

    public void updateMobile(String userId, UpdateRequest.Mobile mobile) {
        User user = this.getUser(userId);
        user.updateMobile(mobile.getMobile());
    }

    public void updateProfileImage(String userId, UpdateRequest.ProfileImage profileImage) {
        User user = this.getUser(userId);
        user.updateProfileImg(profileImage.getProfileImage());
    }
}
