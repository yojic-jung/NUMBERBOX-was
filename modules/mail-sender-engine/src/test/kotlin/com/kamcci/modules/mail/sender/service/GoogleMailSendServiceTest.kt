package com.kamcci.modules.mail.sender.service

import com.kamcci.modules.mail.sender.auth.MailSenderAuthenticator
import com.kamcci.modules.mail.sender.config.GoogleAccountProperty
import com.kamcci.modules.mail.sender.config.GoogleMailProperty
import com.kamcci.modules.mail.sender.processor.LocalMailSendProcessor
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import javax.mail.internet.AddressException

class GoogleMailSendServiceTest {
    companion object {
        private const val recipientEmail = "test@test.com"
        private const val title = "제목"
        private const val contents = "컨텐츠"
    }

    lateinit var googleMailSendService: GoogleMailSendService

    @Test
    fun `메일 전송 - 성공`() {
        // given
        val googleAccountProp = GoogleAccountProperty("test@email.com", "")
        googleMailSendService =
            GoogleMailSendService(
                googleAccountProp,
                GoogleMailProperty("", "", "", "", "", ""),
                LocalMailSendProcessor(),
                MailSenderAuthenticator(googleAccountProp)
            )

        // when
        googleMailSendService.sendHTMLMessage(recipientEmail, title, contents)
    }

    @Test
    fun `발송인 이메일 미작성 메시지 작성 - 실패`() {
        // given
        val googleAccountProp = GoogleAccountProperty("", "")
        googleMailSendService =
            GoogleMailSendService(
                googleAccountProp,
                GoogleMailProperty("", "", "", "", "", ""),
                LocalMailSendProcessor(),
                MailSenderAuthenticator(googleAccountProp)
            )

        // when & then
        assertThrows<AddressException> {
            googleMailSendService.sendHTMLMessage(recipientEmail, title, contents)
        }
    }

    @Test
    fun `수신인 이메일 미작성 메시지 작성 - 실패`() {
        // given
        val googleAccountProp = GoogleAccountProperty("test@email.com", "1234")
        googleMailSendService =
            GoogleMailSendService(
                googleAccountProp,
                GoogleMailProperty("", "", "", "", "", ""),
                LocalMailSendProcessor(),
                MailSenderAuthenticator(googleAccountProp)
            )

        // when & then
        assertThrows<AddressException> {
            googleMailSendService.sendHTMLMessage("", title, contents)
        }
    }
}