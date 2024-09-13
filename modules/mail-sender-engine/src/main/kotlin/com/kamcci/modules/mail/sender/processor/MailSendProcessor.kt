package com.kamcci.modules.mail.sender.processor

import javax.mail.Message

/**
 * 3rd-part-library에 메일 전송을 요청
 * 실질적 메인 전송 처리기
 */
interface MailSendProcessor {
    fun send(message: Message)
}