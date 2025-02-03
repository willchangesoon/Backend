package com.es3.es3backend.user.service;

import com.es3.es3backend.auth.service.ValidationUtil;
import com.es3.es3backend.config.exception.AuthException;
import com.es3.es3backend.config.exception.ErrorCode;
import com.es3.es3backend.user.domain.User;
import com.es3.es3backend.user.domain.UserRepository;
import com.es3.es3backend.user.dto.request.UpdateRequest;
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

    public void updateEmail(User user, UpdateRequest.Email email) {
        user = this.getUser(user);
        if (userRepository.existsByEmail(email.getEmail())){
            throw new AuthException(ErrorCode.REGISTERED_EMAIL);
        }
        validationUtil.checkEmail(email.getEmail());
        user.updateEmail(email.getEmail());
    }

    public void updateName(User user, UpdateRequest.Name name) {
        user = this.getUser(user);
        user.updateName(name.getName());
    }

    public void updatePassword(User user, UpdateRequest.Password password) {
        user = this.getUser(user);
        validationUtil.checkPassword(password.getNewPassword());
        user.updatePassword(password.getOldPassword(), password.getNewPassword());
    }

    public void updateAddress(User user, UpdateRequest.Address address) {
        user = this.getUser(user);
        user.updateAddress(address.getAddress());
    }

    public void updateMobile(User user, UpdateRequest.Mobile mobile) {
        user = this.getUser(user);
        user.updateMobile(mobile.getMobile());
    }

    public void updateProfileImage(User user, UpdateRequest.ProfileImage profileImage) {
        user = this.getUser(user);
        user.updateProfileImg(profileImage.getProfileImage());
    }


    private User getUser(User user) {
        return userRepository.findByEmail(user.getEmail())
                .orElseThrow(() -> new AuthException(ErrorCode.INVALID_EMAIL));
    }
}
