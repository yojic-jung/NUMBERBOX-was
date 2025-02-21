package com.kamcci.numberbox.app.service.mock.usecase.member

import com.kamcci.numberbox.app.service.constant.MockTestConstant.EXCEPTION_ID
import com.kamcci.numberbox.app.service.constant.MockTestConstant.STUB_EXCEPTION_MSG
import com.kamcci.numberbox.app.usecase.member.MemberFollowWriteCase

class MockMemberFollowWriteCase : MemberFollowWriteCase {
    override fun following(followingId: Long, followerId: Long) {
        if (followingId == EXCEPTION_ID) throw RuntimeException(STUB_EXCEPTION_MSG)
    }

    override fun cancel(followingId: Long, followerId: Long) {
        if (followingId == EXCEPTION_ID) throw RuntimeException(STUB_EXCEPTION_MSG)
    }
}