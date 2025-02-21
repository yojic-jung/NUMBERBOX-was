package com.kamcci.numberbox.app.service.mock.port.orm.member

import com.kamcci.numberbox.app.domain.dto.member.MemberPhoneUpdtDto
import com.kamcci.numberbox.app.domain.dto.member.MemberPrivateSignUpDto
import com.kamcci.numberbox.app.port.orm.member.MemberPrivateWriteOrmPort
import com.kamcci.numberbox.app.service.constant.MockTestConstant.FAIL_MEMBER_ID
import java.util.*

class MockMemberPrivateWriteOrmPort : MemberPrivateWriteOrmPort {
    override fun save(memberId: UUID, privateSignUpDto: MemberPrivateSignUpDto): UUID {
        return UUID.randomUUID()
    }

    override fun updatePhoneNumber(phoneUpdtDto: MemberPhoneUpdtDto): Long {
        return if (phoneUpdtDto.memberId == FAIL_MEMBER_ID) 0L else 1L
    }

    override fun updatePrivateToNull(memberId: UUID): Long {
        return if (memberId == FAIL_MEMBER_ID) 0L else 1L
    }
}