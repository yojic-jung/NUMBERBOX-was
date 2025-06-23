package com.kamcci.numberbox.infra.orm.jpa.adapter.redis.hash.member


import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash
import org.springframework.data.redis.core.index.Indexed
import java.util.*

@RedisHash("member", timeToLive = 60L * 60 * 24 * 30)
data class MemberRedisHash(
    @Id
    val id: UUID,
    @Indexed
    val email: String,
    val password: String?,
    val role: List<MemberRoleRedis>,
)