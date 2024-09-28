package com.kamcci.numberbox.app.port.repository.member

import com.kamcci.numberbox.app.domain.dto.member.MemberEmailVerifyCodeSaveDto

/**
 * Def. 이메일 검증 코드 영속화
 */
interface MemberEmailVerifyCodeSaveOrmPort {
    fun save(memberEmailVerifyCodeSaveDto: MemberEmailVerifyCodeSaveDto): Boolean
}