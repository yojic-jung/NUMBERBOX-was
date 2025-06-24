package com.kamcci.numberbox.infra.orm.jpa.adapter.repository.member

import com.kamcci.numberbox.infra.orm.jpa.adapter.base.BaseRepository
import com.kamcci.numberbox.infra.orm.jpa.adapter.entity.member.QMemberEntity.memberEntity
import com.kamcci.numberbox.infra.orm.jpa.adapter.redis.common.CacheNames.MEMBER_EMAIL
import com.kamcci.numberbox.infra.orm.jpa.adapter.redis.config.RedisConfig.Companion.REDIS_MEMBER_CACHE_MANAGER_BEAN
import com.kamcci.numberbox.infra.orm.jpa.adapter.redis.hash.member.MemberRedisHash
import com.kamcci.numberbox.infra.orm.jpa.adapter.redis.hash.member.MemberRoleRedis
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Repository
import java.time.LocalDateTime
import java.util.*

@Repository
class MemberRepositorySupport : BaseRepository() {

    @Cacheable(
        cacheManager = REDIS_MEMBER_CACHE_MANAGER_BEAN,
        cacheNames = [MEMBER_EMAIL],
        key = "#email",
        unless = "#result == null"
    )
    fun findByEmail(email: String): MemberRedisHash? {
        val memberEntity = queryFactory
            .selectFrom(memberEntity)
            .where(memberEntity.email.eq(email))
            .fetchOne()

        return if (memberEntity != null) {
            MemberRedisHash(
                memberEntity.id!!,
                memberEntity.email,
                memberEntity.password,
                memberEntity.role.map { MemberRoleRedis(it.roleName, it.enabled) }
            )
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
