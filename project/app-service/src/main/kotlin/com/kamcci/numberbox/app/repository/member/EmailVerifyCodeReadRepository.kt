package com.kamcci.numberbox.app.repository.member

import com.kamcci.numberbox.app.domain.dto.member.EmailVerifyCodeVo

/**
 * Def. 이메일 검증 코드 영속화
 */
interface EmailVerifyCodeReadRepository {
    fun countByEmail(email: String): Long

    fun findByEmail(email: String): EmailVerifyCodeVo?
}