package com.numberbox.auth.control.config;

public class AuthConfig {
    public static String accessTokenName = "access-token";
    public static String refreshTokenName = "refresh-token";
    public static final long ACCESS_TOKEN_VALID_TIME = 1000L * 60 * 60; // 1시간
    public static final long REFRESH_TOKEN_VALID_TIME = 1000L * 60 * 60 * 24 * 30; // 1달 (로그인 유지 요청한 경우)
    public static final long REFRESH_TOKEN_VALID_TIME_DEFAULT = 1000L * 60 * 60 * 6; // 6시간
}
