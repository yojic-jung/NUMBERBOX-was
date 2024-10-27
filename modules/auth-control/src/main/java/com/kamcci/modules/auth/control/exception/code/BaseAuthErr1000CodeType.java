package com.kamcci.modules.auth.control.exception.code;

/**
 * 1000번대 예외 코드
 */
public enum BaseAuthErr1000CodeType implements BaseAuthErrCodeType {
    BAD_AUTH_REQUEST_ERR_CODE(1000, "잘못된 형식의 인증 요청입니다.");
    private final int code;
    private final String message;

    BaseAuthErr1000CodeType(int code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
