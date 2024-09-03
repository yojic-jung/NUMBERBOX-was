package com.numberbox.modules.auth.control.config;

public class AuthConstantConfig {
    // 클라이언트에 전달할 액세스 토큰 속성명
    public static String ACCESS_TOKEN_NAME = "Authorization";

    public static String TOKEN_STANDARD_PREFIX = "Bearer";

    // 클라이언트에 전달할 리프레시 토큰 속성명
    public static String REFRESH_TOKEN_NAME = "refresh-token";

    // 클라이언트에 전달할 사용자 권한 속성명
    public static String ROLE_NAME = "role";

    // 액세스 토큰 유효시간
    public static final long ACCESS_TOKEN_VALID_TIME = 1000L * 60 * 60; // 1시간

    /**
     ** 로그인 유지 요청 request 속성명
     * - 속성값이 LOGIN_KEEP_VAL인 경우 REFRESH_TOKEN_VALID_TIME_OP_KEEP으로 로그인 유지 시간 결정
     * - 속성값이 LOGIN_KEEP_VAL이 아닌 경우 REFRESH_TOKEN_VALID_TIME으로 로그인 유지 시간 결정
     */
    public static final String LOGIN_KEEP_ATTR = "loginState";

    // 로그인 유지 요청 속성값
    public static final String LOGIN_KEEP_VAL = "keep";

    public static final long REFRESH_TOKEN_VALID_TIME = 1000L * 60 * 60 * 6; // 6시간

    public static final long REFRESH_TOKEN_VALID_TIME_OP_KEEP = 1000L * 60 * 60 * 24 * 30; // 1달 (로그인 유지 요청한 경우)
}
