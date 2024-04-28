package com.numberbox.members.restapi.controller;

import com.numberbox.members.service.MembersService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.Map;

// todo auth controller auth로 이동
@Controller
public class MembersAuthController {

    private final MembersService membersService;

    public MembersAuthController(MembersService membersService) {
        this.membersService = membersService;
    }

    /*
     * @PostMapping("/login") public HashMap<String, Object> login(@ModelAttribute
     * MembersDto membersDto, HttpServletRequest request, HttpServletResponse
     * response) { HashMap<String, Object> map = membersService.login(membersDto,
     * request); Cookie refreshTokenCookie = new Cookie("refresh-token",
     * (String)map.get("refreshToken")); String loginState =
     * (String)request.getParameter("loginState"); if(loginState !=null &&
     * loginState.equals("keep")) { refreshTokenCookie.setMaxAge(60*60*24*60); }
     * response.setHeader("access-token", (String)map.get("accessToken"));
     * response.addCookie(refreshTokenCookie);
     *
     * HashMap<String, Object> returnMap = new HashMap<>(); returnMap.put("isLogin",
     * map.get("isLogin")); return returnMap; }
     */

//    @GetMapping("/loginSuccess")
//    public Map<String, Object> loginSuccess(HttpServletRequest request, HttpServletResponse response) {
//        Cookie refreshTokenCookie = new Cookie("refresh-token", refreshToken);
//        refreshTokenCookie.setPath("/"); // context-path를 myWasApi로 설정하면서 쿠키 Path가 /myWasApi로 바뀜 다시 / 루트 컨텐스트로 쿠키 패쓰 설정
//        refreshTokenCookie.setHttpOnly(true);
//        refreshTokenCookie.setSecure(true);
//        if (loginState != null && loginState.equals("keep")) {
//            refreshTokenCookie.setMaxAge((int) (JwtUtil.REFRESH_TOKEN_VALID_TIME / 1000));
//        } else {
//            refreshTokenCookie.setMaxAge(60 * 60 * 6); // 6시간
//        }
//
//        response.addCookie(refreshTokenCookie);
//
//        /*
//         * ResponseCookie cookie = ResponseCookie.from("refresh-token", refreshToken)
//         * .path("/") .sameSite("None") .httpOnly(true) .secure(true)
//         * .maxAge(60*60*24*30) .build();
//         *
//         * response.setHeader("Set-Cookie", cookie.toString());
//         */
//        Map<String, Object> map = new HashMap<String, Object>();
//        map.put("isLogin", true);
//        System.out.println("loginSuccess");
//        return map;
//    }

    @GetMapping("/delRefreshToken")
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        membersService.delRefreshToken(request, response);
    }

//    @PostMapping("/loginFail")
//    public Map<String, Object> loginFailure(HttpServletRequest request) {
//        String customErrMsg = (String) request.getAttribute("customErrMsg");
//
//        Map<String, Object> map = new HashMap<>();
//        map.put("isSuccess", false);
//        map.put("customErrMsg", customErrMsg);
//        return map;
//    }

    @RequestMapping("/accessDenied")
    public Map<String, Object> accessDenied() {
        Map<String, Object> map = new HashMap<>();
        map.put("existMsg", true);
        map.put("serverMsg", "해당 요청에 접근 권한이 없습니다.");
        return map;
    }

    @GetMapping(value = "/createEmailIdCode")
    public Map<String, Object> createEmailIdCode(HttpServletRequest request) {
        String email = request.getParameter("email");
        return membersService.createEmailIdCode(email);
    }

}
