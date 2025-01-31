package com.kamcci.numberbox.app.service.stub.usecase.member

import com.kamcci.numberbox.app.domain.dto.member.MemberPasswdConfirmDto
import com.kamcci.numberbox.app.domain.dto.member.MemberPasswdUpdtDto
import com.kamcci.numberbox.app.domain.dto.member.MemberPrivateSignUpDto
import com.kamcci.numberbox.app.domain.dto.member.MemberSignUpDto
import com.kamcci.numberbox.app.domain.vo.member.MemberSignUpResultVo
import com.kamcci.numberbox.app.usecase.member.MemberWriteCase
import java.util.*

class MockMemberWriteCase : MemberWriteCase {
    override fun signup(signUpDto: MemberSignUpDto, privateSignUpDto: MemberPrivateSignUpDto?): MemberSignUpResultVo {
        TODO("Not yet implemented")
    }

    override fun updatePassword(updtDto: MemberPasswdUpdtDto): Boolean {
        TODO("Not yet implemented")
    }

    override fun confirmPassword(confirmDto: MemberPasswdConfirmDto): Boolean {
        TODO("Not yet implemented")
    }

    override fun updateTmpPassword(id: List<UUID>) {
        TODO("Not yet implemented")
    }
}