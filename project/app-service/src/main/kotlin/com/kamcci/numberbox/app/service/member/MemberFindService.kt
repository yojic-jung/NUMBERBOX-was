package com.kamcci.numberbox.app.service.member

import com.kamcci.numberbox.app.domain.dto.port.email.EmailCodeMessageDto
import com.kamcci.numberbox.app.domain.dto.port.email.EmailMessageTemplate
import com.kamcci.numberbox.app.domain.system_construction.Aliases
import com.kamcci.numberbox.app.domain.system_construction.UseCase
import com.kamcci.numberbox.app.port.email.member.MemberVerifyCodeEmailPort
import com.kamcci.numberbox.app.port.repository.member.MemberModifyOrmPort
import com.kamcci.numberbox.app.port.repository.member.MemberReadOrmPort
import com.kamcci.numberbox.app.usecase.member.MemberFindUseCase

@UseCase
class MemberFindService(
    private val memberReadOrmPort: MemberReadOrmPort,
    private val memberModifyOrmPort: MemberModifyOrmPort,
    private val memberVerifyCodeEmailPort: MemberVerifyCodeEmailPort,
    @Aliases("password")
    private val emailMessageTemplate: EmailMessageTemplate
) : MemberFindUseCase {

    companion object {
        const val TMP_PASSWD_LENGTH = 40
    }

    override fun findMyEmail(userName: String, phoneNumber: String): String? {
        return memberReadOrmPort.findEmailByUsernameAndPhone(userName, phoneNumber)
    }

    override fun findMyPassword(email: String): Boolean {
        val isExist = memberReadOrmPort.existsByEmail(email)
        // 이메일 존재하는 경우
        if (isExist) {
            // 임시 비밀번호로 변경
            val tmpPassword = makeTmpPassword()
            memberModifyOrmPort.updatePassword(email, tmpPassword)

            // 임시 비밀번호 메시지 전송
            val msgDto = EmailCodeMessageDto(email, tmpPassword)
            memberVerifyCodeEmailPort.send(msgDto, emailMessageTemplate)
        }
        return isExist
    }


    fun makeTmpPassword(): String {
        val chars = ('A'..'Z') + ('a'..'z') + ('0'..'9') + "!@#%*()-_+[]{};:,.?".toList()
        return (1..TMP_PASSWD_LENGTH)
            .map { chars.random() }
            .joinToString("")
    }

}