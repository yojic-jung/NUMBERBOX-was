package com.kamcci.numberbox.email.adapter

import com.kamcci.modules.mail.sender.service.MailSendService
import com.kamcci.numberbox.app.domain.dto.port.email.EmailCodeMessageDto
import com.kamcci.numberbox.app.domain.dto.port.email.EmailMessageTemplate
import com.kamcci.numberbox.app.port.email.member.MemberVerifyCodeEmailPort
import org.springframework.stereotype.Service

@Service
class MemberVerifyCodeEmailAdapter(
    private val mailSendService: MailSendService,
) : MemberVerifyCodeEmailPort {
    override fun send(msgDto: EmailCodeMessageDto, msgTmpl: EmailMessageTemplate) {
        mailSendService.sendHTMLMessage(msgDto.recipientEmail, msgTmpl.title, msgTmpl.getContent(msgDto.code))
    }

}
