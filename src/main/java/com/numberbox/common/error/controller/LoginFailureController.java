package com.numberbox.common.error.controller;

import com.numberbox.common.error.AuthResponse;
import com.numberbox.common.error.port.in.LoginFailureUseCase;
import com.numberbox.common.util.ResponseUtil;
import com.numberbox.security.exception.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Map;

import static com.numberbox.common.error.AuthResponse.*;

@Controller
public class LoginFailureController {
    private final LoginFailureUseCase loginFailureUseCase;

    public LoginFailureController(LoginFailureUseCase loginFailureUseCase) {
        this.loginFailureUseCase = loginFailureUseCase;
    }

    @PostMapping("/loginFail")
    public ResponseEntity<Map<String, Object>> loginFailProcess(HttpServletRequest request) {
        final Exception exception = (Exception) request.getAttribute("auth.error.exception");
        final String userEmail = (String) request.getAttribute("username");

        // 클라이언트의 로그인 요청 형식이 잘못됨
        if (exception instanceof BadAuthRequestException) {
            return response(BAD_AUTH_REQUEST);
        }

        // 계정 존재하지 않음
        if (exception instanceof UserNotFoundException) {
            return response(USER_NOT_FOUND);
        }

        // 비밀번호 불일치
        if (exception instanceof PasswordMissMatchException) {
            // 과도한 비밀번호 불일치 요청시 계정 비활성화
            final boolean isDisabled = loginFailureUseCase.disableUserIfFailCountOver(userEmail);
            return response(isDisabled ? DISABLE_USER : PASSWORD_MISS_MATCH);
        }

        // 비활성화된 계정
        if (exception instanceof DisabledUserException) {
            // 계정 비활성화 유효시간이 지난 경우 다시 활성화
            boolean isAfterDisableTime = loginFailureUseCase.ableUserIfDisableTimeOver(userEmail);
            return response(isAfterDisableTime ? ABLE_USER : DISABLE_USER);
        }

        // 서버 예외
        return response(false, AUTH_SERVER_ERROR);
    }

    private ResponseEntity<Map<String, Object>> response(AuthResponse authResponse) {
        return ResponseUtil.response(authResponse.getMessage(), authResponse.getStatus());
    }

    private ResponseEntity<Map<String, Object>> response(boolean showMessage, AuthResponse authResponse) {
        return ResponseUtil.response(showMessage, authResponse.getMessage(), authResponse.getStatus());
    }

}

