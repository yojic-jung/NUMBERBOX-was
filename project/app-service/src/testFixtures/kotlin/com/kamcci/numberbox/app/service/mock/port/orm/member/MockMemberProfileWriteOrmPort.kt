package com.kamcci.numberbox.app.service.mock.port.orm.member

import com.kamcci.numberbox.app.domain.dto.member.MemberProfileImgUpdtDto
import com.kamcci.numberbox.app.domain.enumeration.member.ProfileType
import com.kamcci.numberbox.app.port.orm.member.MemberProfileWriteOrmPort
import com.kamcci.numberbox.app.service.constant.MockTestConstant.FAIL_MEMBER_ID
import java.util.*

class MockMemberProfileWriteOrmPort : MemberProfileWriteOrmPort {
    override fun save(uuid: UUID, nickName: String): Long {
        return if (uuid == FAIL_MEMBER_ID) 0L else 1L
    }

    override fun updateProfileTypeByMemberId(memberId: UUID, profileType: ProfileType): Long {
        return if (memberId == FAIL_MEMBER_ID) 0L else 1L
    }

    override fun updateImgByMemberId(updateDto: MemberProfileImgUpdtDto): Long {
        return if (updateDto.memberId == FAIL_MEMBER_ID) 0L else 1L
    }

    override fun updateNicknameByMemberId(memberId: UUID, nickname: String): Long {
        return if (memberId == FAIL_MEMBER_ID) 0L else 1L
    }

    override fun updateHwpDownCntByMemberId(hwpDownCnt: Int): Long {
        return 1L
    }
}