package com.kamcci.numberbox.app.port.repository.member

import com.kamcci.numberbox.app.domain.vo.member.MemberEmailVerifyCodeVo

/**
 * Def. 회원 검증 인증코드 영속화
 */
interface MemberVerifyCodeReadOrmPort {
    fun countByEmail(email: String): Long

    fun findByEmail(email: String): MemberEmailVerifyCodeVo?
}