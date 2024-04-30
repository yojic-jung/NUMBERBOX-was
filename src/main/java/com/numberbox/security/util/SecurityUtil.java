package com.numberbox.security.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.numberbox.security.exception.AuthInternalException;
import com.numberbox.security.provider.JwtUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static jakarta.servlet.http.HttpServletResponse.*;

// todo 코드 리팩토링
public class SecurityUtil {
    private SecurityUtil() {
    }

    private static final ObjectMapper obj = new ObjectMapper();

    public static void respondInternalServerError(HttpServletResponse response, String msg) {
        sendResponse(response, SC_INTERNAL_SERVER_ERROR, msg);
    }

    public static void respondUnAuthroized(HttpServletResponse response, String msg) {
        sendResponse(response, SC_UNAUTHORIZED, msg);
    }

    public static void respondOkWithToken(HttpServletRequest request, HttpServletResponse response,
                                          String accessToken, String refreshToken, List<String> role,
                                          String msg) {
        String loginState = request.getParameter("loginState");
        Cookie refreshTokenCookie = makeRefreshTokenCookie(refreshToken, loginState);

        response.setHeader("access-token", accessToken);
        response.addCookie(refreshTokenCookie);
        response.setHeader("role", role.toString());

        sendResponse(response, SC_OK, msg);
    }

    private static Cookie makeRefreshTokenCookie(String refreshToken, String loginState) {
        Cookie refreshTokenCookie = new Cookie("refresh-token", refreshToken);
        refreshTokenCookie.setPath("/");
        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setSecure(true);
        if (loginState != null && loginState.equals("keep")) {
            refreshTokenCookie.setMaxAge((int) (JwtUtil.REFRESH_TOKEN_VALID_TIME / 1000));
        } else {
            refreshTokenCookie.setMaxAge(60 * 60 * 6); // 6시간
        }
        return refreshTokenCookie;
    }

    private static void sendResponse(HttpServletResponse response, int statusCode, String msg) {
        response.setStatus(statusCode);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");


        Map<String, Object> map = new HashMap<>();
        map.put("code", statusCode);
        map.put("timestamp", LocalDateTime.now().toString());
        map.put("message", msg);

        try {
            PrintWriter printWriter = response.getWriter();
            printWriter.write(obj.writeValueAsString(map));
            printWriter.flush();
        } catch (IOException ex) {
            //  todo 여기로 빠질시 응답 어떻게 나가는지 테스트 필요
            throw new AuthInternalException("IOException 발생");
        }
    }
}
