package com.kamcci.numberbox.app.email.sender.member

import com.kamcci.numberbox.app.domain.dto.member.MemberEmailCodeMessageDto

/**
 * 이메일 검증 코드 발송
 */
interface EmailVerifyCodeSender {

    // 검증 코드 수신인 이메일로 전송
    fun send(memberEmailCodeMessageDto: MemberEmailCodeMessageDto)
}