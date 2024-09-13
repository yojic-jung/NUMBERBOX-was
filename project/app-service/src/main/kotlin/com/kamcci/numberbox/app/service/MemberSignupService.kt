package com.kamcci.numberbox.app.service

import com.kamcci.numberbox.app.domain.system_construction.TXExecute
import com.kamcci.numberbox.app.domain.system_construction.UseCase
import com.kamcci.numberbox.app.member.EmailCodeMessageDto
import com.kamcci.numberbox.app.member.EmailVerifyCodeSaveDto
import com.kamcci.numberbox.app.repository.member.EmailIDCodeCmdRepository
import com.kamcci.numberbox.app.usecase.member.EmailVerifyCodeSendUseCase
import com.kamcci.numberbox.app.usecase.member.MemberSignupUseCase
import java.util.*

@UseCase
class MemberSignupService(
    val emailIDCodeSaveDto: EmailIDCodeCmdRepository,
    val emailVerifyCodeSendUseCase: EmailVerifyCodeSendUseCase
) : MemberSignupUseCase {
    @TXExecute
    override fun createEmailCode(email: String): Boolean {
        // 이메일 검증 코드 uuid 생성
        val code = UUID.randomUUID().toString()
        val emailCodeSaveDto = EmailVerifyCodeSaveDto(email, code)

        // 검증 코드 이메일 발송
        val message = EmailCodeMessageDto(email, code)
        emailVerifyCodeSendUseCase.send(message)

        // 검증 코드 저장
        return emailIDCodeSaveDto.save(emailCodeSaveDto)
    }
}