package com.kamcci.numberbox.email.adapter

import com.kamcci.modules.mail.sender.service.MailSendService
import com.kamcci.numberbox.app.domain.dto.member.MemberEmailCodeMessageDto
import com.kamcci.numberbox.app.port.email.member.MemberVerifyCodeEmailPort
import org.springframework.stereotype.Service

@Service
class MemberVerifyCodeEmailAdapter(
    private val mailSendService: MailSendService,
) : MemberVerifyCodeEmailPort {
    override fun send(memberEmailCodeMessageDto: MemberEmailCodeMessageDto) {
        mailSendService.sendHTMLMessage(
            memberEmailCodeMessageDto.recipientEmail,
            memberEmailCodeMessageDto.getTitle(),
            memberEmailCodeMessageDto.getContents(),
        )
    }
}
