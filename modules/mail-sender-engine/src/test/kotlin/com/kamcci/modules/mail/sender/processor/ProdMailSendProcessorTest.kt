package com.kamcci.modules.mail.sender.processor

import com.kamcci.modules.mail.sender.enums.HttpContentType
import com.kamcci.modules.mail.sender.exception.MailSendFailException
import io.mockk.every
import io.mockk.mockkStatic
import io.mockk.unmockkStatic
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import javax.mail.Authenticator
import javax.mail.Message
import javax.mail.Session
import javax.mail.Transport
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage

class ProdMailSendProcessorTest {
    @Test
    fun `ProdMailSendProcessor 실행 여부 검증 - 성공`() {
        // given - 메시지 설정
        val session = Session.getInstance(System.getProperties(), object : Authenticator() {
        })
        val message = MimeMessage(session)

        // 실제 메시지 전송은 모킹
        mockkStatic(Transport::class)
        every { Transport.send(message) } returns Unit


        // when
        ProdMailSendProcessor().send(message)

        // then
        verify {
            Transport.send(message)
        }

        // 후처리 - 스태틱 모킹 제거
        unmockkStatic(Transport::class)
    }

    @Test
    fun `메시지 전송 실패 예외 반환 - 성공`() {
        // given
        val message = makeMessage()

        // when & then
        assertThrows<MailSendFailException> {
            ProdMailSendProcessor().send(message)
        }
    }

    // 메시지 생성
    private fun makeMessage(): Message {
        val mailProps = System.getProperties()
        mailProps["mail.smtp.host"] = ""
        mailProps["mail.smtp.port"] = ""
        mailProps["mail.smtp.auth"] = ""
        mailProps["mail.smtp.ssl.enable"] = ""
        mailProps["mail.smtp.ssl.trust"] = ""
        mailProps["mail.smtp.ssl.protocols"] = ""

        // 2. 세션 설정(구글 계정 인증)
        val session = Session.getInstance(mailProps, object : Authenticator() {

        })
        session.debug = true

        // 3. message 작성
        return MimeMessage(session).apply {
            // 발신자 셋팅
            setFrom(InternetAddress("test@test.com"))
            // 수신자셋팅
            // 제목셋팅
            subject = "title"
            // 내용셋팅
            setContent("contents", HttpContentType.HTML.type)
        }
    }
}
