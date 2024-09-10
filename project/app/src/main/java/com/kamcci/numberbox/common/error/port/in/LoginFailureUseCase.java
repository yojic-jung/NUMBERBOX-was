package com.kamcci.numberbox.common.error.port.in;

/**
 * 로그인 실패시 처리
 */
public interface LoginFailureUseCase {
    /**
     * 과도한 실패 요청시 계정 비활성화
     */
    boolean disableUserIfFailCountOver(String userEmail);

    /**
     * 비활성화 유효기간이 지나면 계정을 활성화
     */
    boolean ableUserIfDisableTimeOver(String userEmail);
}
