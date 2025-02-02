package com.kamcci.numberbox.app.service.stub.port.orm.member

import com.kamcci.numberbox.app.port.orm.member.MemberWriteOrmPort
import com.kamcci.numberbox.app.service.constant.MockTestConstant.FAIL_EMAIL
import com.kamcci.numberbox.app.service.constant.MockTestConstant.FAIL_MEMBER_ID
import java.time.LocalDateTime
import java.util.*

class MockMemberWriteOrmPort : MemberWriteOrmPort {
    override fun save(email: String, password: String): UUID {
        return UUID.randomUUID()
    }

    override fun drop(memberId: UUID): Long {
        return if (memberId == FAIL_MEMBER_ID) 0L else 1L
    }

    override fun updatePassword(memberId: UUID, password: String): Long {
        return if (memberId == FAIL_MEMBER_ID) 0L else 1L
    }

    override fun updatePassword(memberId: List<UUID>, password: String?): Long {
        return if (memberId.contains(FAIL_MEMBER_ID)) 0L else 1L
    }

    override fun updatePassword(email: String, password: String): Long {
        return if (email == FAIL_EMAIL) 0L else 1L
    }

    override fun updateFailCountById(userId: UUID, failCount: Int): Long {
        return if (userId == FAIL_MEMBER_ID) 0L else 1L
    }

    override fun updateLastFailTimeById(userId: UUID, lastFailTime: LocalDateTime): Long {
        return if (userId == FAIL_MEMBER_ID) 0L else 1L
    }
}