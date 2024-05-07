package com.numberbox.auth.engine.handler;

import com.numberbox.auth.engine.exception.*;
import com.numberbox.auth.exception.*;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Def. 로그인 인증 실패 후처리
 */
@Component
public class LoginFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {
        // 시큐리티 의존성 없는 예외타입으로 전환하여 모듈 호출자가 예외 처리할 수 있도록 전달
        if (exception instanceof UsernameNotFoundException) {
            forwardToFailController(request, response, new UserNotFoundException());
        }
        // password 같은지 비교
        else if (exception instanceof BadCredentialsException) {
            forwardToFailController(request, response, new PasswordMissMatchException());
        }
        // 활성 계정 체크
        else if (exception instanceof DisabledException) {
            forwardToFailController(request, response, new DisabledUserException());

        }
        // 잘못된 형식으로 인증 요청
        else if (exception instanceof BadInputRequestException) {
            forwardToFailController(request, response, new BadAuthRequestException());

        }
        else {
            forwardToFailController(request, response, new AuthInternalServerException());
        }
    }

    /**
     * 실패 후처리 진행할 수 있도록 FailController에게 요청
     */
    private void forwardToFailController(HttpServletRequest request, HttpServletResponse response, Exception exception)
            throws ServletException, IOException {
        request.setAttribute("auth.error.exception", exception);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/loginFail");
        dispatcher.forward(request, response);
    }
}
