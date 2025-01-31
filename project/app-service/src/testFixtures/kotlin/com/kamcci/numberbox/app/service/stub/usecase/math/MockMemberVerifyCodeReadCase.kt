package com.kamcci.numberbox.app.service.stub.usecase.math

import com.kamcci.numberbox.app.domain.dto.member.MemberVerifyCodeDto
import com.kamcci.numberbox.app.usecase.member.MemberVerifyCodeReadCase

class MockMemberVerifyCodeReadCase : MemberVerifyCodeReadCase {
    override fun validate(codeDto: MemberVerifyCodeDto) {
        TODO("Not yet implemented")
    }
}