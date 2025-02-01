package com.kamcci.numberbox.app.service.stub.usecase.member

import com.kamcci.numberbox.app.service.constant.FailConstant.FAIL_EMAIL
import com.kamcci.numberbox.app.usecase.member.MemberLoginFailureUseCase

class MockMemberLoginFailureUseCase : MemberLoginFailureUseCase {
    override fun ableUserIfDisableTimeOver(email: String): Boolean {
        return email != FAIL_EMAIL
    }

    override fun disableUserIfFailCountOver(email: String): Boolean {
        return email != FAIL_EMAIL
    }
}