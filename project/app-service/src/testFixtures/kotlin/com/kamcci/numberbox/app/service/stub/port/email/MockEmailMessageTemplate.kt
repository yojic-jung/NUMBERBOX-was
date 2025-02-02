package com.kamcci.numberbox.app.service.stub.port.email

import com.kamcci.numberbox.app.domain.dto.port.email.EmailMessageTemplate

class MockEmailMessageTemplate : EmailMessageTemplate {
    override val title: String
        get() = "[N명의수학] 모킹 타이틀"

    override fun getContent(code: String): String {
        return "모킹 컨텐츠"
    }
}