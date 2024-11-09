package com.kamcci.numberbox.app.service.member

import com.kamcci.numberbox.app.domain.dto.port.email.EmailCodeMessageDto
import com.kamcci.numberbox.app.domain.dto.port.email.EmailMessageTemplate
import com.kamcci.numberbox.app.domain.exception.BusinessValidException
import com.kamcci.numberbox.app.domain.system_construction.Aliases
import com.kamcci.numberbox.app.domain.system_construction.TXExecute
import com.kamcci.numberbox.app.domain.system_construction.UseCase
import com.kamcci.numberbox.app.port.email.member.MemberVerifyCodeEmailPort
import com.kamcci.numberbox.app.port.etc.MemberPasswordEncoder
import com.kamcci.numberbox.app.port.orm.member.MemberModifyOrmPort
import com.kamcci.numberbox.app.port.orm.member.MemberReadOrmPort
import com.kamcci.numberbox.app.usecase.member.MemberFindUseCase

@UseCase
class MemberFindService(
    private val memberReadOrmPort: MemberReadOrmPort,
    private val memberModifyOrmPort: MemberModifyOrmPort,
    private val memberVerifyCodeEmailPort: MemberVerifyCodeEmailPort,
    private val passwordEncoder: MemberPasswordEncoder,
    @Aliases("password")
    private val emailMessageTemplate: EmailMessageTemplate
) : MemberFindUseCase {

    companion object {
        const val TMP_PASSWD_LENGTH = 40
    }

    @TXExecute
    override fun readMyEmail(userName: String, phoneNumber: String): String? {
        return memberReadOrmPort.readEmailByUsernameAndPhone(userName, phoneNumber)
    }

    @TXExecute
    override fun readMyPassword(email: String) {
        memberReadOrmPort.existsByEmail(email).let {
            if (!it) throw BusinessValidException("해당 계정이 존재하지 않습니다.")
        }
        // 이메일 존재하는 경우
        // 임시 비밀번호로 변경
        val tmpPassword = makeTmpPassword()
        val encodedPassword = passwordEncoder.encode(tmpPassword)
        memberModifyOrmPort.updatePassword(email, encodedPassword)

        // 임시 비밀번호 메시지 전송
        val msgDto = EmailCodeMessageDto(email, tmpPassword)
        memberVerifyCodeEmailPort.send(msgDto, emailMessageTemplate)
    }

    private fun makeTmpPassword(): String {
        val chars = ('A'..'Z') + ('a'..'z') + ('0'..'9') + "!@#%*()-_+[]{};:,.?".toList()
        return (1..TMP_PASSWD_LENGTH)
            .map { chars.random() }
            .joinToString("")
    }

}