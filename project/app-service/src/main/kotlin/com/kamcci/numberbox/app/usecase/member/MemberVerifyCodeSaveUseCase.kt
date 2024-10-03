package com.kamcci.numberbox.app.usecase.member

import com.kamcci.numberbox.app.domain.enumeration.member.VerifyCodeType

interface MemberVerifyCodeSaveUseCase {
    // 회원가입 절차에서 사용자 이메일 검증 목적
    fun createVerifyCode(email: String, codeType: VerifyCodeType): Boolean

}