package com.kamcci.numberbox.app.service.stub.usecase.member

import com.kamcci.numberbox.app.domain.dto.member.MemberVerifyCodeDto
import com.kamcci.numberbox.app.service.constant.FailConstant.FAIL_EMAIL
import com.kamcci.numberbox.app.usecase.member.MemberVerifyCodeReadCase

class MockMemberVerifyCodeReadCase : MemberVerifyCodeReadCase {
    override fun validate(codeDto: MemberVerifyCodeDto) {
        if (codeDto.email == FAIL_EMAIL) throw RuntimeException("")
    }
}