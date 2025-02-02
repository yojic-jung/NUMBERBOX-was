package com.kamcci.numberbox.app.service.stub.usecase.member

import com.kamcci.numberbox.app.service.constant.MockTestConstant.FAIL_ID
import com.kamcci.numberbox.app.usecase.member.MemberFollowReadCase

class MockMemberFollowReadCase : MemberFollowReadCase {
    override fun readFollowingByFollower(profileId: Long): List<Long> {
        return if (profileId == FAIL_ID) listOf() else listOf(1L, 2L, 3L)
    }

    override fun readFollowerByFollowing(profileId: Long): List<Long> {
        return if (profileId == FAIL_ID) listOf() else listOf(1L, 2L, 3L)
    }

    override fun countFollower(followingId: Long): Long {
        return 10L
    }

    override fun existFollow(followingId: Long, followerId: Long): Boolean {
        return followingId != FAIL_ID
    }

}