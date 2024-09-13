package com.kamcci.modules.mail.sender.service

import com.kamcci.modules.mail.sender.config.GoogleAccountProperty
import com.kamcci.modules.mail.sender.config.GoogleMailProperty
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito.mockStatic
import org.mockito.kotlin.any
import javax.mail.MessagingException
import javax.mail.Transport
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
        val googleMailProp = GoogleMailProperty("", "", "", "", "", "")
        googleMailSendService = GoogleMailSendService(googleAccountProp, googleMailProp)

        // when
        mockStatic(Transport::class.java).`when`<Unit> { Transport.send(any()) }.then { } // 실제 전송은 모킹
        googleMailSendService.sendHTMLMessage(recipientEmail, title, contents)
    }

    @Test
    fun `발송인 이메일 미작성 - 실패`() {
        // given
        val googleAccountProp = GoogleAccountProperty("", "")
        val googleMailProp = GoogleMailProperty("", "", "", "", "", "")
        googleMailSendService = GoogleMailSendService(googleAccountProp, googleMailProp)

        // when & then
        assertThrows<AddressException> {
            googleMailSendService.sendHTMLMessage(recipientEmail, title, contents)
        }
    }

    @Test
    fun `수신인 이메일 미작성 - 실패`() {
        // given
        val googleAccountProp = GoogleAccountProperty("test@email.com", "1234")
        val googleMailProp = GoogleMailProperty("", "", "", "", "", "")
        googleMailSendService = GoogleMailSendService(googleAccountProp, googleMailProp)

        // when & then
        assertThrows<AddressException> {
            googleMailSendService.sendHTMLMessage("", title, contents)
        }
    }

    @Test
    fun `호스트 정보 미작성 - 실패`() {
        // given
        val googleAccountProp = GoogleAccountProperty("test@email.com", "1234")
        val googleMailProp = GoogleMailProperty("", "", "", "", "", "")
        googleMailSendService = GoogleMailSendService(googleAccountProp, googleMailProp)

        // when & then
        assertThrows<MessagingException> {
            googleMailSendService.sendHTMLMessage(recipientEmail, title, contents)
        }
    }
}