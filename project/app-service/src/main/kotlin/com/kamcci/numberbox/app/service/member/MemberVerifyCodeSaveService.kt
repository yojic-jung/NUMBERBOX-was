package com.kamcci.numberbox.app.service.member

import com.kamcci.numberbox.app.domain.dto.member.MemberEmailCodeMessageDto
import com.kamcci.numberbox.app.domain.dto.member.MemberVerifyCodeSaveDto
import com.kamcci.numberbox.app.domain.enumeration.member.VerifyCodeType
import com.kamcci.numberbox.app.domain.system_construction.TXExecute
import com.kamcci.numberbox.app.domain.system_construction.UseCase
import com.kamcci.numberbox.app.port.email.member.MemberVerifyCodeEmailPort
import com.kamcci.numberbox.app.port.repository.member.MemberVerifyCodeSaveOrmPort
import com.kamcci.numberbox.app.usecase.member.MemberVerifyCodeSaveUseCase
import java.util.*

@UseCase
class MemberVerifyCodeSaveService(
    private val verifyCodeSaveDto: MemberVerifyCodeSaveOrmPort,
    // 메일 처리기
    private val memberVerifyCodeEmailPort: MemberVerifyCodeEmailPort,
) : MemberVerifyCodeSaveUseCase {


    @TXExecute
    override fun createVerifyCode(email: String, codeType: VerifyCodeType): Boolean {
        // 인증 코드 uuid 생성
        val code = UUID.randomUUID().toString()
        val emailCodeSaveDto = MemberVerifyCodeSaveDto(email, codeType, code)

        // 인증 코드 이메일 발송
        val message = MemberEmailCodeMessageDto(email, code)
        memberVerifyCodeEmailPort.send(message)

        // 검증 코드 저장
        return verifyCodeSaveDto.save(emailCodeSaveDto)
    }
}