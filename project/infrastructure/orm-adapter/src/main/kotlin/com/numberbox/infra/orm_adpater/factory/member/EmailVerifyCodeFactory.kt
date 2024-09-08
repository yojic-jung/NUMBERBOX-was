package com.numberbox.infra.orm_adpater.factory.member

import com.numberbox.app.domain.member.EmailVerifyCodeSaveDto
import com.numberbox.infra.orm_adpater.entity.EmailVerifyCodeEntity
import org.springframework.stereotype.Component

@Component
class EmailVerifyCodeFactory {
    /**
     * 최초 생성시 email과 idCode만 필요
     */
    fun save(emailVerifyCodeSaveDto: EmailVerifyCodeSaveDto) = EmailVerifyCodeEntity()
        .apply {
            email = emailVerifyCodeSaveDto.email
            verifyCode = emailVerifyCodeSaveDto.verifyCode
        }
}
