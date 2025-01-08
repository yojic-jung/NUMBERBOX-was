package com.kamcci.numberbox.email.adapter

import com.kamcci.modules.mail.sender.service.MailSendService
import com.kamcci.numberbox.app.domain.dto.port.email.EmailCodeMessageDto
import com.kamcci.numberbox.email.template.EmailVerifyMessageTemplate
import com.kamcci.numberbox.email.template.PasswordMessageTemplate
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.mockito.Mockito.mock

class MemberVerifyCodeEmailAdapterTest {

    companion object {
        const val recipientEmail = "test@test.com"
        const val code = "temp-code"
    }

    private val mailSendService: MailSendService = mock()
    private val memberVerifyCodeEmailAdapter = MemberVerifyCodeEmailAdapter(mailSendService)

    @Test
    fun `비밀번호 발송 - 성송`() {
        // given
        val messageDto = EmailCodeMessageDto(recipientEmail, code)

        // when & then
        assertDoesNotThrow {
            memberVerifyCodeEmailAdapter.send(messageDto, PasswordMessageTemplate())
        }
    }

    @Test
    fun `이메일 인증코드 발송 - 성송`() {
        // given
        val messageDto = EmailCodeMessageDto(recipientEmail, code)

        // when & then
        assertDoesNotThrow {
            memberVerifyCodeEmailAdapter.send(messageDto, EmailVerifyMessageTemplate())
        }
    }

}