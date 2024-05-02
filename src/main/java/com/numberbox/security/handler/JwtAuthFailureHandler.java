package com.numberbox.security.handler;

import com.numberbox.security.dto.InvalidRefreshTokenEvent;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

/**
 * Def. 로그인 인증 실패 후처리
 */
@Component
public class JwtAuthFailureHandler {

    private final ApplicationEventPublisher eventPublisher;

    public JwtAuthFailureHandler(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }


    public void onAuthenticationFailure(HttpServletResponse response, Exception exception) {
//        if (exception instanceof RefreshTokenNullException) {
//            ResponseUtil.respondUnAuthroized(response, "다시 로그인 해주시기 바랍니다.");
//        } else if (exception instanceof RefreshTokenExpirationException) {
//            String refreshToken = ((RefreshTokenExpirationException) exception).getRefreshToken();
//            eventPublish(refreshToken);
//            ResponseUtil.respondUnAuthroized(response, "로그인 유효기간이 종료되었습니다.\n다시 로그인 해주시기 바랍니다.");
//        } else if (exception instanceof RefreshTokenNotMachingException) {
//            String refreshToken = ((RefreshTokenNotMachingException) exception).getRefreshToken();
//            eventPublish(refreshToken);
//            ResponseUtil.respondUnAuthroized(response, "비정상적인 인증 요청입니다.\n다시 로그인 해주시기 바랍니다.");
//        } else if (exception instanceof JwtInvalidException) {
//            ResponseUtil.respondUnAuthroized(response, "로그인 유효기간이 종료되었습니다.\n다시 로그인 해주시기 바랍니다.");
//        } else {
//            // 서버 에러
//            ResponseUtil.respondInternalServerError(response, "로그인 인증 과정에 실패 하였습니다.");
//        }

    }

    // 기존 리프레시 토큰 지워야함
    private void eventPublish(String refreshToken) {
        eventPublisher.publishEvent(new InvalidRefreshTokenEvent(refreshToken));
    }
}
