package com.kamcci.numberbox.app.port.repository.member

import com.kamcci.numberbox.app.domain.vo.member.MemberEmailVerifyCodeVo

/**
 * Def. 이메일 검증 코드 영속화
 */
interface MemberEmailVerifyCodeReadOrmPort {
    fun countByEmail(email: String): Long

    fun findByEmail(email: String): MemberEmailVerifyCodeVo?
}