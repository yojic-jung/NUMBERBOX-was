package com.kamcci.numberbox.app.service.mock.port.orm.member

import com.kamcci.numberbox.app.port.orm.member.MemberWriteOrmPort
import com.kamcci.numberbox.app.service.constant.MockTestConstant.FAIL_EMAIL
import com.kamcci.numberbox.app.service.constant.MockTestConstant.FAIL_MEMBER_ID
import java.time.LocalDateTime
import java.util.*

class MockMemberWriteOrmPort : MemberWriteOrmPort {
    /**
     * 테스트시마다 직접 인스턴스 생성하여 사용하는 경우에만 사용(공유객체로 사용시 동시성 문제 발생함)
     */
    var isUpdateFail = false
    var executeCnt = 0

    override fun save(email: String, password: String): UUID {
        return UUID.randomUUID()
    }

    override fun drop(memberId: UUID): Long {
        executeCnt++
        return if (memberId == FAIL_MEMBER_ID) 0L else 1L
    }

    override fun updatePassword(memberId: UUID, password: String): Long {
        return if (memberId == FAIL_MEMBER_ID || isUpdateFail) 0L else 1L
    }

    override fun updatePassword(memberId: List<UUID>, password: String?): Long {
        executeCnt++
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