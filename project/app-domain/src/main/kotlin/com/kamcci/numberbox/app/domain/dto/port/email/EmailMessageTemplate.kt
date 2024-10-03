package com.kamcci.numberbox.app.domain.dto.port.email

/**
 * 이메일 발송 메시지 템플릿 형식
 */
interface EmailMessageTemplate {
    val title: String
    fun getContent(code: String): String
}