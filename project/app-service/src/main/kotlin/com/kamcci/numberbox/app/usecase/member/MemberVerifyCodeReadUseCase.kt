package com.kamcci.numberbox.app.usecase.member

import com.kamcci.numberbox.app.domain.dto.member.MemberVerifyCodeDto
import com.kamcci.numberbox.app.domain.vo.member.MemberVerifyCodeResultVo

/**
 * 인증 코드 검증
 */
interface MemberVerifyCodeReadUseCase {
    // 인증 코드 검증
    fun validate(codeDto: MemberVerifyCodeDto): MemberVerifyCodeResultVo
}