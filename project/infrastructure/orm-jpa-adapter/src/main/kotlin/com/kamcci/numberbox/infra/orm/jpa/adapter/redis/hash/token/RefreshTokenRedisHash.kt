package com.kamcci.numberbox.infra.orm.jpa.adapter.redis.hash.token

import com.kamcci.numberbox.infra.orm.jpa.adapter.common.CacheNames.REFRESH_TOKEN
import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash
import java.io.Serializable
import java.util.*

@RedisHash(REFRESH_TOKEN, timeToLive = 60L * 60 * 24 * 30)
data class RefreshTokenRedisHash(
    @Id
    val token: String, // refresh-token
    val memberId: UUID,
) : Serializable