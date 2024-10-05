package com.kamcci.numberbox.app.usecase.member

import com.kamcci.numberbox.app.domain.enumeration.member.VerifyCodeType

/**
 * 인증 코드 생성
 */
interface MemberVerifyCodeSaveUseCase {
    /**
     * 회원 인증 코드 생성
     *
     * 1. 인증코드 생성
     * 2. 인증 코드 이메일 발송
     * 3. 인증 코드 db 저장
     */

    fun createVerifyCode(email: String, codeType: VerifyCodeType): String

}