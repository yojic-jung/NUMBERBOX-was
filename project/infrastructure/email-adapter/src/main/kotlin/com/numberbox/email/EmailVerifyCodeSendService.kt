package com.numberbox.email

import com.kamcci.modules.mail.sender.service_impl.MailSendService
import com.numberbox.app.domain.member.EmailCodeMessageDto
import com.numberbox.app.usecase.member.EmailVerifyCodeSendUseCase
import org.springframework.stereotype.Service

@Service
class EmailVerifyCodeSendService(
    private val mailSendService: MailSendService,
) : EmailVerifyCodeSendUseCase {
    override fun send(emailCodeMessageDto: EmailCodeMessageDto) {
        mailSendService.sendHTMLMessage(
            emailCodeMessageDto.recipientEmail,
            emailCodeMessageDto.getTitle(),
            emailCodeMessageDto.getContents(),
        )
    }
}
