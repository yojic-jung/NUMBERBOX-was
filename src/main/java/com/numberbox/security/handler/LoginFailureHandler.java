package com.numberbox.security.handler;

import com.numberbox.security.exception.*;
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
        // 시큐리티 의존성 없는 예외타입으로 전환하여 전달
        Exception errorException;
        if (exception instanceof UsernameNotFoundException) {
            errorException = new UserNotFoundException();
        }
        // password 같은지 비교
        else if (exception instanceof BadCredentialsException) {
            errorException = new PasswordDisMatchException();
        }
        // 활성 계정 체크
        else if (exception instanceof DisabledException) {
            errorException = new DisabledUserException();
        }
        // 잘못된 형식으로 인증 요청
        else if (exception instanceof BadInputRequestException) {
            errorException = new BadAuthRequestException();
        }
        else {
            errorException = new AuthInternalServerException();
        }

        request.setAttribute("auth.error.exception", errorException);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/loginFail");
        dispatcher.forward(request, response);
    }

}
