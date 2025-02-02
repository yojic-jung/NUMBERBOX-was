package com.kamcci.numberbox.app.service.stub.usecase.member

import com.kamcci.numberbox.app.domain.dto.member.MemberVerifyCodeDto
import com.kamcci.numberbox.app.service.constant.MockTestConstant.EXCEPTION_EMAIL
import com.kamcci.numberbox.app.service.constant.MockTestConstant.STUB_EXCEPTION_MSG
import com.kamcci.numberbox.app.usecase.member.MemberVerifyCodeReadCase

class MockMemberVerifyCodeReadCase : MemberVerifyCodeReadCase {
    override fun validate(codeDto: MemberVerifyCodeDto) {
        if (codeDto.email == EXCEPTION_EMAIL) throw RuntimeException(STUB_EXCEPTION_MSG)
    }
}