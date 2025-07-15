package com.kamcci.numberbox.infra.orm.jpa.adapter.mock

import com.kamcci.numberbox.app.port.orm.member.MemberWriteOrmPort
import com.kamcci.numberbox.infra.orm.jpa.adapter.repository.member.MemberWriteRepository
import java.time.LocalDateTime
import java.util.UUID

class MockMemberWriteRepository : MemberWriteOrmPort {

    var executeCnt = 0

    override fun save(email: String, password: String): UUID {
        executeCnt++
        return UUID.randomUUID()
    }

    override fun drop(memberId: UUID): Long {
        executeCnt++
        return 1L
    }

    override fun updatePassword(memberId: UUID, password: String): Long {
        executeCnt++
        return 1L
    }

    override fun updatePassword(
        memberId: List<UUID>,
        password: String?
    ): Long {
        executeCnt++
        return 1L
    }

    override fun updatePassword(email: String, password: String): Long {
        executeCnt++
        return 1L
    }

    override fun updateFailCountById(userId: UUID, failCount: Int): Long {
        executeCnt++
        return 1L
    }

    override fun updateLastFailTimeById(userId: UUID, lastFailTime: LocalDateTime): Long {
        executeCnt++
        return 1L
    }

}