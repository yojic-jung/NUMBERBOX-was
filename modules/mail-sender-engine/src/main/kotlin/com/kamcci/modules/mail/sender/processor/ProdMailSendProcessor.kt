package com.kamcci.modules.mail.sender.processor

import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Service
import javax.annotation.processing.Generated
import javax.mail.Message
import javax.mail.Transport

/**
 * Def. 운영 환경에서 주입되는 메일 전송 처리기
 * Desc. 운영 환경에서만 메일을 전송함
 */
@Generated
@Profile("prod")
@Service
class ProdMailSendProcessor : MailSendProcessor {
    override fun send(message: Message) {
        Transport.send(message)
    }
}