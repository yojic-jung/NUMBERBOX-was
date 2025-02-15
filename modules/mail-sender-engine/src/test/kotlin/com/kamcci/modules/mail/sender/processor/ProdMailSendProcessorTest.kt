package com.kamcci.modules.mail.sender.processor

import com.kamcci.modules.mail.sender.enums.HttpContentType
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.context.annotation.Profile
import javax.mail.Authenticator
import javax.mail.Session
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage
import kotlin.reflect.full.findAnnotation

class ProdMailSendProcessorTest {
    @Test
    fun `ProdMailSendProcessor 인스턴스화 가능 구조 - 성공`() {
        // given
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
        val message = MimeMessage(session)
        message.apply {
            // 발신자 셋팅
            setFrom(InternetAddress("test@test.com"))
            // 수신자셋팅
            // 제목셋팅
            subject = "title"
            // 내용셋팅
            setContent("contents", HttpContentType.HTML.type)
        }

        // config는 스프링이 내부적으로 인스턴스화 진행
        try {
            ProdMailSendProcessor().send(message)
        } catch (e: Exception) {

        }
    }

    @Test
    fun `ProdMailSendProcessor는 prod에서만 사용 가능 - 성공`() {
        val annotation = ProdMailSendProcessor::class.findAnnotation<Profile>() as Profile

        // then
        Assertions.assertThat(annotation).isNotNull
        Assertions.assertThat(annotation.value).contains("prod")
    }

}
