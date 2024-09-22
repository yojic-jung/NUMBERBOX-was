package com.kamcci.numberbox.app.repository.member

import com.kamcci.numberbox.app.domain.dto.member.MemberEmailVerifyCodeVo

/**
 * Def. 이메일 검증 코드 영속화
 */
interface MemberEmailVerifyCodeReadRepository {
    fun countByEmail(email: String): Long

    fun findByEmail(email: String): MemberEmailVerifyCodeVo?
}