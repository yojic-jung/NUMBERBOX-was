package com.kamcci.numberbox.app.service.member

import com.kamcci.numberbox.app.domain.dto.port.email.EmailCodeMessageDto
import com.kamcci.numberbox.app.domain.dto.port.email.EmailMessageTemplate
import com.kamcci.numberbox.app.domain.exception.BusinessInValidException
import com.kamcci.numberbox.app.domain.system.construction.Aliases
import com.kamcci.numberbox.app.domain.system.construction.TXExecute
import com.kamcci.numberbox.app.domain.system.construction.UseCase
import com.kamcci.numberbox.app.port.email.member.MemberVerifyCodeEmailPort
import com.kamcci.numberbox.app.port.etc.MemberPasswordEncoder
import com.kamcci.numberbox.app.port.orm.member.MemberWriteOrmPort
import com.kamcci.numberbox.app.usecase.member.MemberFindReadCase
import com.kamcci.numberbox.app.usecase.member.MemberReadCase

@UseCase
class MemberFindService(
    private val memberReadCase: MemberReadCase,
    private val memberWriteOrmPort: MemberWriteOrmPort,
    private val memberVerifyCodeEmailPort: MemberVerifyCodeEmailPort,
    private val passwordEncoder: MemberPasswordEncoder,
    @Aliases("password")
    private val emailMessageTemplate: EmailMessageTemplate
) : MemberFindReadCase {

    companion object {
        const val TMP_PASSWD_LENGTH = 40
        const val NOT_EXIST_USER = "존재하지 않는 계정입니다."
    }

    @TXExecute
    override fun readMyEmail(userName: String, phoneNumber: String): String? {
        return memberReadCase.readEmailByUsernameAndPhone(userName, phoneNumber)
    }

    @TXExecute
    override fun readMyPassword(email: String) {
        memberReadCase.existsByEmail(email).let {
            if (!it) throw BusinessInValidException(NOT_EXIST_USER)
        }
        // 이메일 존재하는 경우
        // 임시 비밀번호로 변경
        val tmpPassword = makeTmpPassword()
        val encodedPassword = passwordEncoder.encode(tmpPassword)
        memberWriteOrmPort.updatePassword(email, encodedPassword)

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