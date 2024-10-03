package com.kamcci.numberbox.app.port.repository.member

import com.kamcci.numberbox.app.domain.enumeration.member.VerifyCodeType
import com.kamcci.numberbox.app.domain.vo.member.MemberEmailVerifyCodeVo

/**
 * Def. 회원 검증 인증코드 영속화
 */
interface MemberVerifyCodeReadOrmPort {
    fun countByEmailAndCodeType(email: String, codeType: VerifyCodeType): Long

    fun findByEmailAndCodeType(email: String, codeType: VerifyCodeType): MemberEmailVerifyCodeVo?
}