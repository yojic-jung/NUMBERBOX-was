package com.kamcci.modules.mail.sender.processor

import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Service
import javax.mail.Message

/**
 * Def. 운영 환경 아닌 경우에 주입되는 메일 전송 처리기
 * Desc. 아무것도 하지 않음(운영 환경 아닌 경우 메일 전송 안함)
 */
@Profile("!prod")
@Service
class LocalMailSendProcessor : MailSendProcessor {
    override fun send(message: Message) {
        // 아무것도 하지 않음
    }
}