package com.numberbox.members.restapi.controller;

import com.numberbox.members.appservice.usecase.MembersAuthUseCase;
import com.numberbox.members.restapi.dto.request.MembersRequest;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class MembersRegisterController {

    private final MembersAuthUseCase membersUseCase;

    public MembersRegisterController(MembersAuthUseCase membersUseCase) {
        this.membersUseCase = membersUseCase;
    }

    @PostMapping("/signup")
    public Map<String, Object> signup(HttpServletRequest request, MembersRequest members,
                                      HttpServletResponse response) {
        Map<String, Object> map = new HashMap<String, Object>();
        Map<String, String> returnMap = membersUseCase.signUp(request, members);
        returnMap.put("isSuccess", "not");
        String isSuccess = returnMap.get("isSuccess");
        if (isSuccess.equals("success")) {
            Cookie refreshTokenCookie = new Cookie("refresh-token", returnMap.get("refreshToken"));
            response.setHeader("access-token", returnMap.get("accessToken"));
            refreshTokenCookie.setPath("/"); // context-path를 myWasApi로 설정하면서 쿠키 Path가 /myWasApi로 바뀜 다시 / 루트 컨텐스트로 쿠키 패쓰
            // 설정
            refreshTokenCookie.setMaxAge(60 * 60 * 6); // 6시간
            refreshTokenCookie.setHttpOnly(true);
            refreshTokenCookie.setSecure(true);
            response.addCookie(refreshTokenCookie);
        }
        map.put("isSuccess", isSuccess);
        return map;
    }
}
