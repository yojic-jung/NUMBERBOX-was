package com.kamcci.numberbox.app.domain.dto.port.email

/**
 * 인증 코드 이메일 메시지 양식
 */
data class EmailCodeMessageDto(
    val recipientEmail: String, // 수신인
    val code: String, // 코드(인증 코드, 임시 비밀번호 등)
)