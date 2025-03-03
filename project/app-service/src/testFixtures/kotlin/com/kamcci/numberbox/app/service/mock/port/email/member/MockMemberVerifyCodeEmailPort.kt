package com.kamcci.numberbox.app.service.mock.port.email.member

import com.kamcci.numberbox.app.domain.dto.port.email.EmailCodeMessageDto
import com.kamcci.numberbox.app.domain.dto.port.email.EmailMessageTemplate
import com.kamcci.numberbox.app.port.email.member.MemberVerifyCodeEmailPort

class MockMemberVerifyCodeEmailPort : MemberVerifyCodeEmailPort {
    // 실행횟수
    var executeCnt = 0

    override fun send(msgDto: EmailCodeMessageDto, msgTmpl: EmailMessageTemplate) {
        executeCnt++
    }
}