package com.kamcci.numberbox.app.service.stub.usecase.member

import com.kamcci.numberbox.app.domain.vo.member.MemberProfileImgVo
import com.kamcci.numberbox.app.domain.vo.member.MemberProfileVo
import com.kamcci.numberbox.app.usecase.member.MemberProfileReadCase
import java.util.*

class MockMemberProfileReadCase : MemberProfileReadCase {
    override fun readByMemberId(memberId: UUID): MemberProfileVo? {
        TODO("Not yet implemented")
    }

    override fun readByProfileId(profileId: Long): MemberProfileVo? {
        TODO("Not yet implemented")
    }

    override fun readByProfileIdList(profileId: List<Long>): List<MemberProfileVo> {
        TODO("Not yet implemented")
    }

    override fun readProfileImgByMemberId(memberId: UUID): MemberProfileImgVo? {
        TODO("Not yet implemented")
    }

    override fun readProfileIdByMemberId(memberId: UUID): Long? {
        TODO("Not yet implemented")
    }
}