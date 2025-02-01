package com.kamcci.numberbox.app.service.stub.usecase.member

import com.kamcci.numberbox.app.usecase.member.MemberFollowReadCase

class MockMemberFollowReadCase : MemberFollowReadCase {
    override fun readFollowingByFollower(profileId: Long): List<Long> {
        return if (profileId != 2L) listOf(1L, 2L, 3L) else listOf()
    }

    override fun readFollowerByFollowing(profileId: Long): List<Long> {
        return if (profileId != 2L) listOf(1L, 2L, 3L) else listOf()
    }

    override fun countFollower(followingId: Long): Long {
        return 10L
    }

    override fun existFollow(followingId: Long, followerId: Long): Boolean {
        return followingId != 2L
    }

}