package com.kamcci.numberbox.infra.orm.jpa.adapter.factory.member

import com.kamcci.numberbox.app.domain.dto.member.MemberVerifyCodeSaveDto
import com.kamcci.numberbox.infra.orm.jpa.adapter.entity.member.MemberVerifyCodeEntity
import java.time.LocalDateTime

object MemberVerifyCodeFactory {
    /**
     * 최초 생성시 email과 verifyCode만 필요
     */
    fun makeSaveEntity(memberVerifyCodeSaveDto: MemberVerifyCodeSaveDto) = MemberVerifyCodeEntity()
        .apply {
            email = memberVerifyCodeSaveDto.email
            codeType = memberVerifyCodeSaveDto.codeType
            verifyCode = memberVerifyCodeSaveDto.verifyCode
            tryCnt = 0
        }

    fun makeUpdateEntity(memberVerifyCodeSaveDto: MemberVerifyCodeSaveDto) = MemberVerifyCodeEntity()
        .apply {
            email = memberVerifyCodeSaveDto.email
            codeType = memberVerifyCodeSaveDto.codeType
            verifyCode = memberVerifyCodeSaveDto.verifyCode
            sysCreateTime = LocalDateTime.now()
        }
}
