package com.kamcci.numberbox.app.repository.member

import com.kamcci.numberbox.app.domain.dto.member.EmailVerifyCodeSaveDto

/**
 * Def. 이메일 검증 코드 영속화
 */
interface EmailVerifyCodeModifyRepository {
    fun save(emailVerifyCodeSaveDto: EmailVerifyCodeSaveDto): Boolean
}