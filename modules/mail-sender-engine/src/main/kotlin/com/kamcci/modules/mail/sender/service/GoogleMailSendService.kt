package com.kamcci.modules.mail.sender.service

import com.kamcci.modules.mail.sender.config.GoogleAccountProperty
import com.kamcci.modules.mail.sender.config.GoogleMailProperty
import com.kamcci.modules.mail.sender.enums.HttpContentType
import com.kamcci.modules.mail.sender.processor.MailSendProcessor
import org.springframework.stereotype.Service
import javax.mail.Authenticator
import javax.mail.Message
import javax.mail.Session
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage

@Service
class GoogleMailSendService(
    private val accountProp: GoogleAccountProperty,
    private val googleProp: GoogleMailProperty,
    private val mailSendProcessor: MailSendProcessor,
    private val authenticator: Authenticator,
) : MailSendService {
    override fun sendHTMLMessage(
        recipientEmail: String,
        title: String,
        contents: String,
    ) {
        send(recipientEmail, title, contents, HttpContentType.HTML)
    }

    private fun send(
        recipientEmail: String,
        title: String,
        contents: String,
        contentType: HttpContentType,
    ) {
        // 1. 메일 서버 속성 설정
        val mailProps = System.getProperties()
        mailProps["mail.smtp.host"] = googleProp.host
        mailProps["mail.smtp.port"] = googleProp.port
        mailProps["mail.smtp.auth"] = googleProp.auth
        mailProps["mail.smtp.ssl.enable"] = googleProp.sslEnable
        mailProps["mail.smtp.ssl.trust"] = googleProp.sslTrust
        mailProps["mail.smtp.ssl.protocols"] = googleProp.protocols

        // 2. 세션 설정(구글 계정 인증)
        val session = Session.getInstance(mailProps, authenticator)
        session.debug = true

        // 3. message 작성
        val message = MimeMessage(session)
        message.apply {
            // 발신자 셋팅
            setFrom(InternetAddress(accountProp.email))
            // 수신자셋팅
            setRecipient(Message.RecipientType.TO, InternetAddress(recipientEmail))
            // 제목셋팅
            subject = title
            // 내용셋팅
            setContent(contents, contentType.type)
        }

        // 메일 전송
        mailSendProcessor.send(message)
    }
}
