package com.kamcci.numberbox.app.service.mock.port.orm.member

import com.kamcci.numberbox.app.port.orm.member.MemberFollowWriteOrmPort
import com.kamcci.numberbox.app.service.constant.MockTestConstant.FAIL_ID

class MockMemberFollowWriteOrmPort : MemberFollowWriteOrmPort {
    override fun save(followingId: Long, followerId: Long): Boolean {
        return followingId != FAIL_ID
    }

    override fun delete(followingId: Long, followerId: Long): Long {
        return if (followingId == FAIL_ID) 0L else 1L
    }
}