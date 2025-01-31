package com.kamcci.numberbox.app.service.stub.usecase.member

import com.kamcci.numberbox.app.usecase.member.MemberFollowReadCase

class MockMemberFollowReadCase : MemberFollowReadCase {
    override fun readFollowingByFollower(profileId: Long): List<Long> {
        TODO("Not yet implemented")
    }

    override fun readFollowerByFollowing(profileId: Long): List<Long> {
        TODO("Not yet implemented")
    }

    override fun countFollower(followingId: Long): Long {
        TODO("Not yet implemented")
    }

    override fun existFollow(followingId: Long, followerId: Long): Boolean {
        TODO("Not yet implemented")
    }

}