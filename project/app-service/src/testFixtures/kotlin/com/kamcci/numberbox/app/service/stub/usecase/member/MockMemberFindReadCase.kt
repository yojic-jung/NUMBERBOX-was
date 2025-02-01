package com.kamcci.numberbox.app.service.stub.usecase.member

import com.kamcci.numberbox.app.service.constant.FailConstant.FAIL_EMAIL
import com.kamcci.numberbox.app.service.constant.FailConstant.FAIL_USER_NAME
import com.kamcci.numberbox.app.usecase.member.MemberFindReadCase

class MockMemberFindReadCase : MemberFindReadCase {
    override fun readMyEmail(userName: String, phoneNumber: String): String? {
        return if (userName == FAIL_USER_NAME) null else "success"
    }

    override fun sendNewTempPassword(email: String) {
        if (email == FAIL_EMAIL) throw RuntimeException()
    }
}