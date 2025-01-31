package com.kamcci.numberbox.app.service.stub.usecase.member

import com.kamcci.numberbox.app.domain.enumeration.member.VerifyCodeType
import com.kamcci.numberbox.app.usecase.member.MemberVerifyCodeWriteCase

class MockMemberVerifyCodeWriteCase : MemberVerifyCodeWriteCase {
    override fun createVerifyCode(email: String, codeType: VerifyCodeType): String {
        TODO("Not yet implemented")
    }
}