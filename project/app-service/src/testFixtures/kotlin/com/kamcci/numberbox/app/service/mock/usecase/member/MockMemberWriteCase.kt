package com.kamcci.numberbox.app.service.mock.usecase.member

import com.kamcci.numberbox.app.domain.dto.member.MemberPasswdConfirmDto
import com.kamcci.numberbox.app.domain.dto.member.MemberPasswdUpdtDto
import com.kamcci.numberbox.app.domain.dto.member.MemberPrivateSignUpDto
import com.kamcci.numberbox.app.domain.dto.member.MemberSignUpDto
import com.kamcci.numberbox.app.domain.vo.member.MemberSignUpResultVo
import com.kamcci.numberbox.app.service.constant.MockTestConstant.EXCEPTION_EMAIL
import com.kamcci.numberbox.app.service.constant.MockTestConstant.FAIL_MEMBER_ID
import com.kamcci.numberbox.app.service.constant.MockTestConstant.STUB_EXCEPTION_MSG
import com.kamcci.numberbox.app.usecase.member.MemberWriteCase
import java.util.*

class MockMemberWriteCase : MemberWriteCase {
    override fun signup(signUpDto: MemberSignUpDto, privateSignUpDto: MemberPrivateSignUpDto?): MemberSignUpResultVo {
        if (signUpDto.email == EXCEPTION_EMAIL) throw RuntimeException(STUB_EXCEPTION_MSG)
        else return MemberSignUpResultVo(
            UUID.randomUUID(),
            signUpDto.email,
            listOf("USER")
        )
    }

    override fun updatePassword(updtDto: MemberPasswdUpdtDto): Boolean {
        return updtDto.memberId != FAIL_MEMBER_ID
    }

    override fun confirmPassword(confirmDto: MemberPasswdConfirmDto): Boolean {
        return confirmDto.memberId != FAIL_MEMBER_ID
    }

    override fun updateTmpPassword(id: List<UUID>) {
    }
}