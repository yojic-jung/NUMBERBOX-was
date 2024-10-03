package com.kamcci.numberbox.app.port.email.member

import com.kamcci.numberbox.app.domain.dto.port.email.EmailCodeMessageDto
import com.kamcci.numberbox.app.domain.dto.port.email.EmailMessageTemplate

/**
 * 인증 코드 발송
 */
interface MemberVerifyCodeEmailPort {

    // 검증 코드 수신인 이메일로 전송
    fun send(msgDto: EmailCodeMessageDto, msgTmpl: EmailMessageTemplate)
}