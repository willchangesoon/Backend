package com.es3.user.common;

import com.es3.user.config.exception.AuthException;
import com.es3.user.config.exception.ErrorCode;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class ValidationUtil {
    private static final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
    private static final String PASSWORD_REGEX = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";

    public void checkEmail(String email) {
        if(!Pattern.matches(EMAIL_REGEX, email)){
            throw new AuthException(ErrorCode.INVALID_EMAIL);
        }
    }

    public void checkPassword(String password) {
        if (!Pattern.matches(PASSWORD_REGEX, password)) {
            throw new AuthException(ErrorCode.INVALID_PASSWORD);
        }
    }

}
