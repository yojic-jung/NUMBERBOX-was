package com.kamcci.numberbox.app.port.repository.member

import com.kamcci.numberbox.app.domain.dto.member.MemberVerifyCodeSaveDto

/**
 * Def. 회원 검증 인증코드 영속화
 */
interface MemberVerifyCodeSaveOrmPort {
    fun save(memberVerifyCodeSaveDto: MemberVerifyCodeSaveDto): Boolean
}