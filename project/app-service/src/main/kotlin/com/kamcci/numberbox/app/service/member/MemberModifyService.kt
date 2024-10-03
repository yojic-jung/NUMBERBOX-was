package com.kamcci.numberbox.app.service.member

import com.kamcci.numberbox.app.domain.dto.member.MemberDropDto
import com.kamcci.numberbox.app.domain.dto.member.MemberPasswdUpdtDto
import com.kamcci.numberbox.app.domain.dto.member.MemberPhoneUpdtDto
import com.kamcci.numberbox.app.domain.system_construction.UseCase
import com.kamcci.numberbox.app.usecase.member.MemberModifyUseCase

@UseCase
class MemberModifyService : MemberModifyUseCase {
    override fun updatePassword(passwordUpdtDto: MemberPasswdUpdtDto): Boolean {
        // 1. 인증코드 확인

        // 2.
        TODO("Not yet implemented")
    }

    override fun updatePhoneNumber(phoneUpdtDto: MemberPhoneUpdtDto): Boolean {
        TODO("Not yet implemented")
    }

    override fun drop(dropDto: MemberDropDto): Boolean {
        // 1. 인증코드 확인
        TODO("Not yet implemented")
    }
}
