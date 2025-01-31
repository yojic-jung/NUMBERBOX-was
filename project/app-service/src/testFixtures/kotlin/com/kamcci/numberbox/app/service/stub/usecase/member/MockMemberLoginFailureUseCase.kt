package com.kamcci.numberbox.app.service.stub.usecase.member

import com.kamcci.numberbox.app.usecase.member.MemberLoginFailureUseCase

class MockMemberLoginFailureUseCase : MemberLoginFailureUseCase {
    override fun ableUserIfDisableTimeOver(email: String): Boolean {
        TODO("Not yet implemented")
    }

    override fun disableUserIfFailCountOver(email: String): Boolean {
        TODO("Not yet implemented")
    }
}