package com.kamcci.numberbox.email.adapter

import com.kamcci.numberbox.app.domain.dto.port.email.EmailCodeMessageDto
import com.kamcci.numberbox.email.adapter.mock.MockMailSendService
import com.kamcci.numberbox.email.template.EmailVerifyMessageTemplate
import com.kamcci.numberbox.email.template.PasswordMessageTemplate
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow

class MemberVerifyCodeEmailAdapterTest {

    companion object {
        const val RECIPIENT_EMAIL = "test@test.com"
        const val CODE = "temp-code"
    }

    // 테스트 대상
    private val memberVerifyCodeEmailAdapter = MemberVerifyCodeEmailAdapter(MockMailSendService())

    @Test
    fun `비밀번호 발송 - 성공`() {
        // given
        val messageDto = EmailCodeMessageDto(RECIPIENT_EMAIL, CODE)

        // when & then
        assertDoesNotThrow {
            memberVerifyCodeEmailAdapter.send(messageDto, PasswordMessageTemplate())
        }
    }

    @Test
    fun `이메일 인증코드 발송 - 성공`() {
        // given
        val messageDto = EmailCodeMessageDto(RECIPIENT_EMAIL, CODE)

        // when & then
        assertDoesNotThrow {
            memberVerifyCodeEmailAdapter.send(messageDto, EmailVerifyMessageTemplate())
        }
    }

}