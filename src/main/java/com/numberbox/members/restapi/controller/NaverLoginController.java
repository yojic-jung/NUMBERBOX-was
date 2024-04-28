package com.numberbox.members.restapi.controller;

import com.numberbox.members.appservice.usecase.NaverLoginUseCase;
import com.numberbox.members.restapi.dto.request.MembersRequest;
import com.numberbox.security.provider.JwtUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.HashMap;
import java.util.Map;

public class NaverLoginController {

    private final NaverLoginUseCase naverLoginUseCase;

    public NaverLoginController(NaverLoginUseCase naverLoginUseCase) {
        this.naverLoginUseCase = naverLoginUseCase;
    }

    @PostMapping("/naverLogin")
    public Map<String, Object> naverLogin(MembersRequest members, HttpServletRequest request,
                                          HttpServletResponse response) {
        Map<String, Object> map = new HashMap<String, Object>();
        Map<String, String> returnMap = naverLoginUseCase.naverLogin(members, request);
        String isSuccess = returnMap.get("isSuccess");
        if (isSuccess.equals("loginSuccess") || isSuccess.equals("signUpSuccess")) {
            Cookie refreshTokenCookie = new Cookie("refresh-token", returnMap.get("refreshToken"));
            response.setHeader("access-token", returnMap.get("accessToken"));
            response.setHeader("role", returnMap.get("role"));
            String loginState = request.getParameter("loginState");
            if (loginState != null && loginState.equals("keep")) {
                refreshTokenCookie.setMaxAge((int) (JwtUtil.REFRESH_TOKEN_VALID_TIME / 1000));
            } else {
                refreshTokenCookie.setMaxAge(60 * 60 * 6); // 6시간
            }
            refreshTokenCookie.setPath("/"); // context-path를 myWasApi로 설정하면서 쿠키 Path가 /myWasApi로 바뀜 다시 / 루트 컨텐스트로 쿠키 패쓰
            // 설정
            refreshTokenCookie.setHttpOnly(true);
            refreshTokenCookie.setSecure(true);
            response.addCookie(refreshTokenCookie);

        }
        map.put("isSuccess", isSuccess);
        return map;
    }

}
