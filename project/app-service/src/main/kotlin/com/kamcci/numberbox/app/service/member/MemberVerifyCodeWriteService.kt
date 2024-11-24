package com.kamcci.numberbox.app.service.member

import com.kamcci.numberbox.app.domain.dto.member.MemberVerifyCodeSaveDto
import com.kamcci.numberbox.app.domain.dto.port.email.EmailCodeMessageDto
import com.kamcci.numberbox.app.domain.dto.port.email.EmailMessageTemplate
import com.kamcci.numberbox.app.domain.enumeration.member.VerifyCodeType
import com.kamcci.numberbox.app.domain.system_construction.Aliases
import com.kamcci.numberbox.app.domain.system_construction.TXExecute
import com.kamcci.numberbox.app.domain.system_construction.UseCase
import com.kamcci.numberbox.app.port.email.member.MemberVerifyCodeEmailPort
import com.kamcci.numberbox.app.port.orm.member.MemberVerifyCodeSaveOrmPort
import com.kamcci.numberbox.app.usecase.member.MemberVerifyCodeWriteUseCase
import java.util.*

@UseCase
class MemberVerifyCodeWriteService(
    private val memberVerifyCodeSaveOrmPort: MemberVerifyCodeSaveOrmPort,
    private val memberVerifyCodeEmailPort: MemberVerifyCodeEmailPort,
    @Aliases("emailVerify")
    private val emailMessageTemplate: EmailMessageTemplate
) : MemberVerifyCodeWriteUseCase {
    @TXExecute
    override fun createVerifyCode(email: String, codeType: VerifyCodeType): String {
        // 인증 코드 uuid 생성
        val code = UUID.randomUUID().toString()
        val emailCodeSaveDto = MemberVerifyCodeSaveDto(email, codeType, code)

        // 인증 코드 이메일 발송
        val message = EmailCodeMessageDto(email, code)
        memberVerifyCodeEmailPort.send(message, emailMessageTemplate)

        // 검증 코드 저장
        memberVerifyCodeSaveOrmPort.save(emailCodeSaveDto)
        return code
    }
}