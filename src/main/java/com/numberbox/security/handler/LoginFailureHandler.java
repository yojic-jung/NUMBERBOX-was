package com.numberbox.security.handler;

import com.numberbox.security.exception.AuthInternalException;
import com.numberbox.security.exception.DisabledUserException;
import com.numberbox.security.exception.PasswordDisMatchException;
import com.numberbox.security.exception.UserNotFoundException;
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
        Exception errorException;
        if (exception instanceof UsernameNotFoundException) {
            errorException = new UserNotFoundException("해당 계정이 없습니다.");

        }
        // password 같은지 비교
        else if (exception instanceof BadCredentialsException) {
            errorException = new PasswordDisMatchException("비밀번호가 일치하지 않습니다.");
        }
        // 활성 계정 체크
        else if (exception instanceof DisabledException) {
            errorException = new DisabledUserException("비활성 계정입니다.");
        } else {
            errorException = new AuthInternalException();
        }

        // todo 해결필요
        System.out.println("failure");
        request.setAttribute("numberbox.error.exception", errorException);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/loginFail");
        dispatcher.forward(request, response);
        System.out.println("failure1");
    }

}
