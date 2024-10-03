package com.kamcci.numberbox.app.usecase.member

import com.kamcci.numberbox.app.domain.enumeration.member.VerifyCodeType

interface MemberVerifyCodeSaveUseCase {
    // 회원 인증 코드 생성
    fun createVerifyCode(email: String, codeType: VerifyCodeType): Boolean

}