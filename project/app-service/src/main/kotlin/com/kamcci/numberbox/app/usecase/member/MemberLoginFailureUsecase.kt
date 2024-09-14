package com.kamcci.numberbox.app.usecase.member

/**
 * 로그인 실패시 처리
 */
interface MemberLoginFailureUsecase {
    /**
     * 과도한 실패 요청시 계정 비활성화
     */
    fun disableUserIfFailCountOver(email: String): Boolean

    /**
     * 비활성화 유효기간이 지나면 계정을 활성화
     */
    fun ableUserIfDisableTimeOver(email: String): Boolean
}