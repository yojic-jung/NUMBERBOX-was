package com.kamcci.numberbox.app.service.stub.usecase.member

import com.kamcci.numberbox.app.domain.dto.member.MemberProfileImgUpdtDto
import com.kamcci.numberbox.app.domain.enumeration.member.ProfileType
import com.kamcci.numberbox.app.usecase.member.MemberProfileWriteCase
import java.util.*

class MockMemberProfileWriteCase : MemberProfileWriteCase {
    override fun updateProfileTypeByMemberId(memberId: UUID, profileType: ProfileType) {
        TODO("Not yet implemented")
    }

    override fun updateImgByMemberId(updateDto: MemberProfileImgUpdtDto) {
        TODO("Not yet implemented")
    }

    override fun updateNicknameByMemberId(memberId: UUID, nickname: String) {
        TODO("Not yet implemented")
    }

    override fun updateHwpDownCnt(hwpDownCnt: Int): Long {
        TODO("Not yet implemented")
    }
}