package com.kamcci.modules.auth.control.exception.code;

/**
 * 100번대 에러 코드(상태 코드 아닌 에러 코드)
 */
public enum Auth100ErrCodeType implements BaseAuthErrCodeType {
    BAD_AUTH_REQUEST(100, "잘못된 형식의 인증 요청입니다."), //
    DISABLED_USER(101, "비활성 계정입니다."), //
    USER_NOT_FOUND(102, "해당 계정이 존재하지 않습니다."), //
    PASSWORD_MISS_MATCH(103, "비밀번호가 일치하지 않습니다.");
    private final int code;
    private final String message;

    Auth100ErrCodeType(int code, String message) {
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
