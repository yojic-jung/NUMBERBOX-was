package com.kamcci.numberbox.infra.orm.factory.member

import com.kamcci.numberbox.app.member.EmailVerifyCodeSaveDto
import com.kamcci.numberbox.infra.orm.entity.member.MemberEmailVerifyCodeEntity
import org.springframework.stereotype.Component

@Component
class MemberEmailVerifyCodeFactory {
    /**
     * 최초 생성시 email과 idCode만 필요
     */
    fun save(emailVerifyCodeSaveDto: EmailVerifyCodeSaveDto) = MemberEmailVerifyCodeEntity()
        .apply {
            email = emailVerifyCodeSaveDto.email
            verifyCode = emailVerifyCodeSaveDto.verifyCode
        }
}
