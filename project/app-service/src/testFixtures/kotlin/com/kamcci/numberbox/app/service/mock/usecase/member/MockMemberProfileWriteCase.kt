package com.kamcci.numberbox.app.service.mock.usecase.member

import com.kamcci.numberbox.app.domain.dto.member.MemberProfileImgUpdtDto
import com.kamcci.numberbox.app.domain.enumeration.member.ProfileType
import com.kamcci.numberbox.app.service.constant.MockTestConstant.EXCEPTION_MEMBER_ID
import com.kamcci.numberbox.app.service.constant.MockTestConstant.STUB_EXCEPTION_MSG
import com.kamcci.numberbox.app.usecase.member.MemberProfileWriteCase
import java.util.*

class MockMemberProfileWriteCase : MemberProfileWriteCase {
    override fun updateProfileTypeByMemberId(memberId: UUID, profileType: ProfileType) {
        if (memberId == EXCEPTION_MEMBER_ID) throw RuntimeException(STUB_EXCEPTION_MSG)
    }

    override fun updateImgByMemberId(updateDto: MemberProfileImgUpdtDto) {
        if (updateDto.memberId == EXCEPTION_MEMBER_ID) throw RuntimeException(STUB_EXCEPTION_MSG)
    }

    override fun updateNicknameByMemberId(memberId: UUID, nickname: String) {
        if (memberId == EXCEPTION_MEMBER_ID) throw RuntimeException(STUB_EXCEPTION_MSG)
    }

    override fun updateHwpDownCnt(hwpDownCnt: Int): Long {
        return 1L
    }
}