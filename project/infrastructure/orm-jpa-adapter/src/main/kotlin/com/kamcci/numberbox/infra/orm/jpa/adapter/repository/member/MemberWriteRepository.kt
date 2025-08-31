package com.kamcci.numberbox.infra.orm.jpa.adapter.repository.member

import com.kamcci.numberbox.app.port.orm.member.MemberWriteOrmPort
import com.kamcci.numberbox.infra.orm.jpa.adapter.base.BaseRepository
import com.kamcci.numberbox.infra.orm.jpa.adapter.entity.member.MemberEntity
import com.kamcci.numberbox.infra.orm.jpa.adapter.entity.member.QMemberEntity.memberEntity
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Repository
import java.time.LocalDateTime
import java.util.*

@Qualifier("jpaAdapter")
@Repository
class MemberWriteRepository : MemberWriteOrmPort, BaseRepository() {
    override fun save(email: String, password: String): UUID {
        val memberEntity = MemberEntity().apply {
            this.email = email
            this.password = password
        }
        em.persist(memberEntity)
        return memberEntity.id!!
    }

    override fun drop(memberId: UUID): Long {
        return queryFactory
            .update(memberEntity)
            .set(memberEntity.humanStatus, 3)
            .set(memberEntity.sysUpdateTime, LocalDateTime.now())
            .where(memberEntity.id.eq(memberId))
            .execute()
    }

    override fun updatePassword(memberId: UUID, password: String): Long {
        return queryFactory
            .update(memberEntity)
            .set(memberEntity.password, password)
            .set(memberEntity.sysUpdateTime, LocalDateTime.now())
            .where(memberEntity.id.eq(memberId))
            .execute()
    }

    override fun updatePassword(memberId: List<UUID>, password: String?): Long {
        return queryFactory
            .update(memberEntity)
            .set(memberEntity.password, password)
            .set(memberEntity.sysUpdateTime, LocalDateTime.now())
            .where(memberEntity.id.`in`(memberId))
            .execute()
    }

    override fun updatePassword(email: String, password: String): Long {
        return queryFactory
            .update(memberEntity)
            .set(memberEntity.password, password)
            .set(memberEntity.sysUpdateTime, LocalDateTime.now())
            .where(memberEntity.email.eq(email))
            .execute()
    }

    override fun updateFailCountById(userId: UUID, failCount: Int): Long {
        return queryFactory
            .update(memberEntity)
            .set(memberEntity.failCount, failCount)
            .set(memberEntity.lastFailTime, LocalDateTime.now())
            .where(memberEntity.id.eq(userId))
            .execute()
    }

    override fun updateLastFailTimeById(userId: UUID, lastFailTime: LocalDateTime): Long {
        return queryFactory
            .update(memberEntity)
            .set(memberEntity.lastFailTime, lastFailTime)
            .where(memberEntity.id.eq(userId))
            .execute()
    }
}