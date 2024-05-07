package com.numberbox.security.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.numberbox.security.dto.AuthResponse;
import com.numberbox.security.exception.AuthInternalException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

// todo 코드 리팩토링
public class SecurityUtil {
    private SecurityUtil() {
    }

    private static final ObjectMapper obj = new ObjectMapper();

    public static void responseErrMsg(HttpServletResponse response, HttpStatus status, String msg) {
        sendResponse(response, status, true, msg);
    }

    public static void responseErrMsg(HttpServletResponse response, HttpStatus status, boolean showMsg, String msg) {
        sendResponse(response, status, showMsg, msg);
    }

    /**
     * 200 성공 응답 전송
     */
    public static void responseOK(HttpServletResponse response, boolean showMsg, String message) {
        sendResponse(response, AuthResponse.OK.status, showMsg, message);
    }

    public static void responseOK(HttpServletResponse response, String message) {
        sendResponse(response, AuthResponse.OK.status, true, message);
    }

    private static void sendResponse(HttpServletResponse response, HttpStatus status, boolean showMsg, String msg) {
        response.setStatus(status.value());
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        Map<String, Object> map = new HashMap<>();
        map.put("showMsg", showMsg);
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

    public static Cookie makeCookie(String name, String value, int maxAge) {
        return makeCookie(name, value, "/", true, true, maxAge);
    }

    /**
     *  쿠키 생성
     */
    public static Cookie makeCookie(String name, String value, String path, boolean httpOnly, boolean secure, int maxAge) {
        Cookie refreshTokenCookie = new Cookie(name, value);
        refreshTokenCookie.setPath(path);
        refreshTokenCookie.setHttpOnly(httpOnly);
        refreshTokenCookie.setSecure(secure);
        refreshTokenCookie.setMaxAge(maxAge);
        return refreshTokenCookie;
    }
}
