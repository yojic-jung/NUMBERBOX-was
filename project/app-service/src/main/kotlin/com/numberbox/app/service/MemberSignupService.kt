package com.numberbox.app.service

import com.numberbox.app.domain.member.EmailCodeMessageDto
import com.numberbox.app.domain.member.EmailVerifyCodeSaveDto
import com.numberbox.app.domain.system_construction.TxExecute
import com.numberbox.app.domain.system_construction.UseCase
import com.numberbox.app.repository.member.EmailIDCodeCmdRepository
import com.numberbox.app.usecase.member.EmailVerifyCodeSendUseCase
import com.numberbox.app.usecase.member.MemberSignupUseCase
import java.util.*

@UseCase
class MemberSignupService(
    private val emailIDCodeSaveDto: EmailIDCodeCmdRepository,
    private val emailVerifyCodeSendUseCase: EmailVerifyCodeSendUseCase
) : MemberSignupUseCase {
    @TxExecute
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