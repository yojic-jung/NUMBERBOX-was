package com.kamcci.numberbox.app.service.stub.usecase.member

import com.kamcci.numberbox.app.domain.vo.member.MemberProfileVo
import com.kamcci.numberbox.app.usecase.member.MemberProfileFollowReadCase
import java.util.*

class MockMemberProfileFollowReadCase : MemberProfileFollowReadCase {
    override fun readFollowingProfileByMemberId(memberId: UUID): List<MemberProfileVo> {
        TODO("Not yet implemented")
    }

    override fun readFollowerProfileByMemberId(memberId: UUID): List<MemberProfileVo> {
        TODO("Not yet implemented")
    }
}