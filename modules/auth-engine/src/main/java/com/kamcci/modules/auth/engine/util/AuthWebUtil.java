package com.kamcci.modules.auth.engine.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kamcci.modules.auth.control.dto.AuthResponse;
import com.kamcci.modules.auth.engine.exception.AuthInternalException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.util.WebUtils;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * 웹 응답 반환 유틸
 */
public class AuthWebUtil {
    private static final ObjectMapper obj = new ObjectMapper();

    private AuthWebUtil() { }

    public static void responseErrMsg(HttpServletResponse response, HttpStatus status, String msg) {
        sendResponse(response, status.value(), msg);
    }

    public static void responseErrMsg(HttpServletResponse response, int rawStatus, String msg) {
        sendResponse(response, rawStatus, msg);
    }

    /**
     * 200 성공 응답 전송
     */
    public static void responseOK(HttpServletResponse response, String message) {
        sendResponse(response, AuthResponse.LOGIN_OK.statusCode, message);
    }

    private static void sendResponse(HttpServletResponse response, int rawStatus, String msg) {
        response.setStatus(rawStatus);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // 응답값 설정
        Map<String, Object> map = new HashMap<>();
        map.put("timestamp", LocalDateTime.now().toString());
        map.put("status", rawStatus);
        map.put("message", msg);

        // 응닶값 path 추출 및 설정
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if(attributes != null) {
            HttpServletRequest request = attributes.getRequest();
            String requestUri = request.getRequestURI();
            map.put("path", requestUri);
        }

        try {
            PrintWriter printWriter = response.getWriter();
            printWriter.write(obj.writeValueAsString(map));
            printWriter.flush();
        } catch(IOException ex) {
            throw new AuthInternalException("IOException 발생");
        }
    }

    public static String getCookieValue(HttpServletRequest request, String cookieName) {
        Cookie cookie = WebUtils.getCookie(request, cookieName);
        return cookie == null ? null : cookie.getValue();
    }

    public static Cookie makeCookie(String name, String value, int maxAge) {
        return makeCookie(name, value, "/", true, true, maxAge);
    }

    /**
     * 쿠키 생성
     */
    public static Cookie makeCookie(String name, String value, String path, boolean httpOnly, boolean secure,
                                    int maxAge) {
        Cookie refreshTokenCookie = new Cookie(name, value);
        refreshTokenCookie.setPath(path);
        refreshTokenCookie.setHttpOnly(httpOnly);
        refreshTokenCookie.setSecure(secure);
        refreshTokenCookie.setMaxAge(maxAge);
        return refreshTokenCookie;
    }

}
