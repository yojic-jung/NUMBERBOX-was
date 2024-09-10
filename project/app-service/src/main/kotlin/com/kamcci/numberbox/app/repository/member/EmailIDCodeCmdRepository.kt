package com.kamcci.numberbox.app.repository.member

import com.kamcci.numberbox.app.member.EmailVerifyCodeSaveDto

/**
 * Def. 이메일 검증 코드 영속화
 */
interface EmailIDCodeCmdRepository {
    fun save(emailVerifyCodeSaveDto: EmailVerifyCodeSaveDto): Boolean
}