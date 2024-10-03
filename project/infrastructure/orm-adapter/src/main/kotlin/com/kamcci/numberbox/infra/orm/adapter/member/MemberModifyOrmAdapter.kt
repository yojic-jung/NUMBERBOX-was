package com.kamcci.numberbox.infra.orm.adapter.member

import com.kamcci.numberbox.app.domain.dto.member.MemberPasswdUpdtDto
import com.kamcci.numberbox.app.port.repository.member.MemberModifyOrmPort
import com.kamcci.numberbox.infra.orm.base.BaseRepository
import org.springframework.stereotype.Repository
import java.time.LocalDateTime
import java.util.*

@Repository
class MemberModifyOrmAdapter : MemberModifyOrmPort, BaseRepository() {
    override fun updatePhoneNumber() {
        TODO("Not yet implemented")
    }

    override fun updatePassword(passwordUpdtDto: MemberPasswdUpdtDto): Boolean {
        TODO("Not yet implemented")
    }

    override fun drop(memberId: UUID) {
        TODO("Not yet implemented")
    }

    override fun updateFailCountById(userId: UUID, failCount: Int): Boolean {
        TODO("Not yet implemented")
    }

    override fun updateLastFailTimeById(userId: UUID, lastFailTime: LocalDateTime): Boolean {
        TODO("Not yet implemented")
    }
}