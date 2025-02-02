package com.kamcci.numberbox.app.service.stub.port.email.member

import com.kamcci.numberbox.app.domain.dto.port.email.EmailCodeMessageDto
import com.kamcci.numberbox.app.domain.dto.port.email.EmailMessageTemplate
import com.kamcci.numberbox.app.port.email.member.MemberVerifyCodeEmailPort

class MockMemberVerifyCodeEmailPort : MemberVerifyCodeEmailPort {
    override fun send(msgDto: EmailCodeMessageDto, msgTmpl: EmailMessageTemplate) {
        
    }
}