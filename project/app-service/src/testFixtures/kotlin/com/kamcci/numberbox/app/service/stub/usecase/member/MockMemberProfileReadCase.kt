package com.kamcci.numberbox.app.service.stub.usecase.member

import com.kamcci.numberbox.app.domain.vo.member.MemberProfileImgVo
import com.kamcci.numberbox.app.domain.vo.member.MemberProfileVo
import com.kamcci.numberbox.app.service.dummy.MemberDummyData.getMemberProfileImgVo
import com.kamcci.numberbox.app.service.dummy.MemberDummyData.getMemberProfileVo
import com.kamcci.numberbox.app.service.dummy.MemberDummyData.getMemberProfileVoList
import com.kamcci.numberbox.app.usecase.member.MemberProfileReadCase
import java.util.*

class MockMemberProfileReadCase : MemberProfileReadCase {
    override fun readByMemberId(memberId: UUID): MemberProfileVo {
        return getMemberProfileVo()
    }

    override fun readByProfileId(profileId: Long): MemberProfileVo? {
        return if (profileId == 2L) null else getMemberProfileVo()
    }

    override fun readByProfileIdList(profileId: List<Long>): List<MemberProfileVo> {
        return getMemberProfileVoList(100)
    }

    override fun readProfileImgByMemberId(memberId: UUID): MemberProfileImgVo {
        return getMemberProfileImgVo()
    }

    override fun readProfileIdByMemberId(memberId: UUID): Long {
        return 1L
    }
}