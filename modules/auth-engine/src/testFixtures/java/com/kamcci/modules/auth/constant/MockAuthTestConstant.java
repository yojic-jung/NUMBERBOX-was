package com.kamcci.modules.auth.constant;

import java.util.UUID;

public class MockAuthTestConstant {
    // 실패 문자열 파라미터
    public static final String FAIL_STRING = "실패";
    // Null을 반환하는 username
    public static final String NULL_USER = "NULL_USER";
    // 실패 토큰
    public static final String FAIL_TOKEN = "13ad5466-cda8-ea4d-9bc7-037cb86fdb20";
    // 실패 MEMBER_ID
    public static final UUID FAIL_MEMBER_ID = UUID.fromString("10ed5466-cda8-ea4d-9bc7-037cb86fdb20");
    // 예외 메시지 문자열
    public static String STUB_EXCEPTION_MSG = "실패 케이스 예외 발생";

}
