package com.kamcci.numberbox.infra.orm.jpa.adapter.repository.member

import com.kamcci.numberbox.app.port.orm.member.MemberWriteOrmPort
import com.kamcci.numberbox.infra.orm.jpa.adapter.base.BaseRepository
import com.kamcci.numberbox.infra.orm.jpa.adapter.entity.member.MemberEntity
import com.kamcci.numberbox.infra.orm.jpa.adapter.entity.member.QMemberEntity.memberEntity
import com.kamcci.numberbox.infra.orm.jpa.adapter.redis.common.CacheNames.MEMBER_EMAIL
import com.kamcci.numberbox.infra.orm.jpa.adapter.redis.repository.member.MemberRedisRepository
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Caching
import org.springframework.stereotype.Repository
import java.time.LocalDateTime
import java.util.*

@Repository
class MemberWriteRepository(
    private val memberRedisRepository: MemberRedisRepository
) : MemberWriteOrmPort, BaseRepository() {
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
        memberRedisRepository.deleteById(memberId)
        return queryFactory
            .update(memberEntity)
            .set(memberEntity.password, password)
            .set(memberEntity.sysUpdateTime, LocalDateTime.now())
            .where(memberEntity.id.eq(memberId))
            .execute()
    }

    override fun updatePassword(memberId: List<UUID>, password: String?): Long {
        memberRedisRepository.deleteAllById(memberId)
        return queryFactory
            .update(memberEntity)
            .set(memberEntity.password, password)
            .set(memberEntity.sysUpdateTime, LocalDateTime.now())
            .where(memberEntity.id.`in`(memberId))
            .execute()
    }

    @Caching(
        evict = [
            CacheEvict(cacheNames = [MEMBER_EMAIL], key = "#email"),
        ]
    )
    override fun updatePassword(email: String, password: String): Long {
        // todo 주석 제거 해도 되는지??
//        memberRedisRepository.deleteByEmail(email)
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