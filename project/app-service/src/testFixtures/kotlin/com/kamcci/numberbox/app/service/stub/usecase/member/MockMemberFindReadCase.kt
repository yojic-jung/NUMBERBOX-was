package com.kamcci.numberbox.app.service.stub.usecase.member

import com.kamcci.numberbox.app.usecase.member.MemberFindReadCase

class MockMemberFindReadCase : MemberFindReadCase {
    override fun readMyEmail(userName: String, phoneNumber: String): String? {
        TODO("Not yet implemented")
    }

    override fun sendNewTempPassword(email: String) {
        TODO("Not yet implemented")
    }
}