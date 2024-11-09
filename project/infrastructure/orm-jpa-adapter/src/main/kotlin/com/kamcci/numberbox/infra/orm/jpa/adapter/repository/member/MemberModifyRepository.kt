package com.kamcci.numberbox.infra.orm.jpa.adapter.repository.member

import com.kamcci.numberbox.app.port.orm.member.MemberModifyOrmPort
import com.kamcci.numberbox.infra.orm.jpa.adapter.base.BaseRepository
import com.kamcci.numberbox.infra.orm.jpa.adapter.entity.member.QMemberEntity.memberEntity
import org.springframework.stereotype.Repository
import java.time.LocalDateTime
import java.util.*

@Repository
class MemberModifyRepository : MemberModifyOrmPort, BaseRepository() {
    override fun updatePassword(memberId: UUID, password: String): Boolean {
        return queryFactory
            .update(memberEntity)
            .set(memberEntity.password, password)
            .set(memberEntity.sysUpdateTime, LocalDateTime.now())
            .where(memberEntity.id.eq(memberId))
            .execute() > 0
    }

    override fun updatePassword(email: String, password: String): Boolean {
        return queryFactory
            .update(memberEntity)
            .set(memberEntity.password, password)
            .set(memberEntity.sysUpdateTime, LocalDateTime.now())
            .where(memberEntity.email.eq(email))
            .execute() > 0
    }


    override fun drop(memberId: UUID) {
        TODO("Not yet implemented")
    }

    override fun updateFailCountById(userId: UUID, failCount: Int): Boolean {
        return queryFactory
            .update(memberEntity)
            .set(memberEntity.failCount, failCount)
            .set(memberEntity.lastFailTime, LocalDateTime.now())
            .where(memberEntity.id.eq(userId))
            .execute() > 0
    }

    override fun updateLastFailTimeById(userId: UUID, lastFailTime: LocalDateTime): Boolean {
        return queryFactory
            .update(memberEntity)
            .set(memberEntity.lastFailTime, lastFailTime)
            .where(memberEntity.id.eq(userId))
            .execute() > 0
    }
}