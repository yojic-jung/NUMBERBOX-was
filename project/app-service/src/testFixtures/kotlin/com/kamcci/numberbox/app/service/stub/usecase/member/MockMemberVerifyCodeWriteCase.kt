package com.kamcci.numberbox.app.service.stub.usecase.member

import com.kamcci.numberbox.app.domain.enumeration.member.VerifyCodeType
import com.kamcci.numberbox.app.service.constant.MockTestConstant.EXCEPTION_EMAIL
import com.kamcci.numberbox.app.service.constant.MockTestConstant.STUB_EXCEPTION_MSG
import com.kamcci.numberbox.app.usecase.member.MemberVerifyCodeWriteCase
import java.util.*

class MockMemberVerifyCodeWriteCase : MemberVerifyCodeWriteCase {
    override fun createVerifyCode(email: String, codeType: VerifyCodeType): String {
        if (email == EXCEPTION_EMAIL) throw RuntimeException(STUB_EXCEPTION_MSG)
        else return UUID.randomUUID().toString()
    }
}