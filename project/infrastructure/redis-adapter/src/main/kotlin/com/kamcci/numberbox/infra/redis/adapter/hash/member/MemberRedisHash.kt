package com.kamcci.numberbox.infra.redis.adapter.hash.member


import com.kamcci.numberbox.infra.persistence.adapter.core.constant.CacheNames
import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash
import org.springframework.data.redis.core.index.Indexed
import java.util.*

@RedisHash(CacheNames.MEMBER, timeToLive = 60L * 60 * 3)
data class MemberRedisHash(
    @Id
    val id: UUID,
    @Indexed
    val email: String,
    val password: String?,
    val role: List<MemberRoleRedis>,
)