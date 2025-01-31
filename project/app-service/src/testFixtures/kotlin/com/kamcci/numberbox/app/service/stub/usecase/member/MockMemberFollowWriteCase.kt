package com.kamcci.numberbox.app.service.stub.usecase.member

import com.kamcci.numberbox.app.usecase.member.MemberFollowWriteCase

class MockMemberFollowWriteCase : MemberFollowWriteCase {
    override fun following(followingId: Long, followerId: Long) {
        TODO("Not yet implemented")
    }

    override fun cancel(followingId: Long, followerId: Long) {
        TODO("Not yet implemented")
    }
}