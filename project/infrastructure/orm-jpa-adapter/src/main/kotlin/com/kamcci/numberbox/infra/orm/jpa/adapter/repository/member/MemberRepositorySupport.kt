package com.kamcci.numberbox.infra.orm.jpa.adapter.repository.member

import com.kamcci.numberbox.infra.orm.jpa.adapter.base.BaseRepository
import com.kamcci.numberbox.infra.orm.jpa.adapter.entity.member.QMemberEntity.memberEntity
import com.kamcci.numberbox.infra.orm.jpa.adapter.redis.hash.member.MemberRedisHash
import com.kamcci.numberbox.infra.orm.jpa.adapter.redis.hash.member.MemberRoleRedis
import com.kamcci.numberbox.infra.orm.jpa.adapter.redis.repository.member.MemberRedisRepository
import org.springframework.stereotype.Repository
import java.time.LocalDateTime
import java.util.*

@Repository
class MemberRepositorySupport(
    private val memberRedisRepository: MemberRedisRepository
) : BaseRepository() {
    fun findByEmail(email: String): MemberRedisHash? {
        val memberEntity = queryFactory
            .selectFrom(memberEntity)
            .where(memberEntity.email.eq(email))
            .fetchOne()

        return if (memberEntity != null) {
            val memberHash = MemberRedisHash(
                memberEntity.id!!,
                memberEntity.email,
                memberEntity.password,
                memberEntity.role.map { MemberRoleRedis(it.roleName, it.enabled) }
            )
            memberRedisRepository.save(memberHash)
            memberHash
        } else {
            null
        }
    }

    fun updateSuccessUser(
        userUniqId: UUID,
        failCount: Int,
        humanStatus: Int,
    ): Long {
        return queryFactory
            .update(memberEntity)
            .set(memberEntity.lastLoginTime, LocalDateTime.now())
            .set(memberEntity.failCount, failCount)
            .set(memberEntity.humanStatus, humanStatus)
            .where(memberEntity.id.eq(userUniqId))
            .execute()
    }
}
