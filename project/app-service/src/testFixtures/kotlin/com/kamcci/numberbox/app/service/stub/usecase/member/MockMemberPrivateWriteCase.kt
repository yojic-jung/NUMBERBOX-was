package com.kamcci.numberbox.app.service.stub.usecase.member

import com.kamcci.numberbox.app.domain.dto.member.MemberPhoneUpdtDto
import com.kamcci.numberbox.app.service.constant.MockTestConstant.FAIL_PHONE_NUMBER
import com.kamcci.numberbox.app.usecase.member.MemberPrivateWriteCase

class MockMemberPrivateWriteCase : MemberPrivateWriteCase {
    override fun updatePhoneNumber(phoneUpdtDto: MemberPhoneUpdtDto): Boolean {
        return phoneUpdtDto.phoneNumber != FAIL_PHONE_NUMBER
    }
}