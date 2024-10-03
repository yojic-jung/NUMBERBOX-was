package com.kamcci.numberbox.app.service.member

import com.kamcci.numberbox.app.usecase.member.MemberFindUseCase

class MemberFindService : MemberFindUseCase {
    override fun findEmail(userName: String, phoneNumber: String): String {
        TODO("Not yet implemented")
    }

    override fun findPasswd(email: String) {
        TODO("Not yet implemented")
    }
}