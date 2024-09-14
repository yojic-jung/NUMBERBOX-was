package com.kamcci.numberbox.email

import com.kamcci.modules.mail.sender.service.MailSendService
import com.kamcci.numberbox.app.domain.member.EmailCodeMessageDto
import com.kamcci.numberbox.app.email.sender.EmailVerifyCodeSender
import org.springframework.stereotype.Service

@Service
class EmailVerifyCodeSender(
    private val mailSendService: MailSendService,
) : EmailVerifyCodeSender {
    override fun send(emailCodeMessageDto: EmailCodeMessageDto) {
        mailSendService.sendHTMLMessage(
            emailCodeMessageDto.recipientEmail,
            emailCodeMessageDto.getTitle(),
            emailCodeMessageDto.getContents(),
        )
    }
}
