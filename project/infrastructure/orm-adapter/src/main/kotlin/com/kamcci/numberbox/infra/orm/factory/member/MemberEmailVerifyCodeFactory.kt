package com.kamcci.numberbox.infra.orm.factory.member

import com.kamcci.numberbox.app.domain.dto.member.MemberEmailVerifyCodeSaveDto
import com.kamcci.numberbox.infra.orm.entity.member.MemberEmailVerifyCodeEntity
import java.time.LocalDateTime

object MemberEmailVerifyCodeFactory {
    /**
     * 최초 생성시 email과 verifyCode만 필요
     */
    fun makeSaveEntity(memberEmailVerifyCodeSaveDto: MemberEmailVerifyCodeSaveDto) = MemberEmailVerifyCodeEntity()
        .apply {
            email = memberEmailVerifyCodeSaveDto.email
            verifyCode = memberEmailVerifyCodeSaveDto.verifyCode
        }

    fun makeUpdateEntity(memberEmailVerifyCodeSaveDto: MemberEmailVerifyCodeSaveDto) = MemberEmailVerifyCodeEntity()
        .apply {
            email = memberEmailVerifyCodeSaveDto.email
            verifyCode = memberEmailVerifyCodeSaveDto.verifyCode
            sysCreateTime = LocalDateTime.now()
        }
}
