package com.kamcci.numberbox.app.usecase.member

import com.kamcci.numberbox.app.domain.dto.member.MemberVerifyCodeDto

/**
 * 인증 코드 검증
 */
interface MemberVerifyCodeReadCase {
    // 인증 코드 검증
    fun validate(codeDto: MemberVerifyCodeDto)
}