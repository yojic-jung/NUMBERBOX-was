package com.kamcci.numberbox.infra.orm.jpa.adapter.redis.repository.token

import com.kamcci.numberbox.infra.orm.jpa.adapter.redis.hash.token.RefreshTokenRedisHash
import org.springframework.data.repository.CrudRepository
import java.util.*

interface RefreshTokenRedisRepository : CrudRepository<RefreshTokenRedisHash, String> {
    override fun findById(token: String): Optional<RefreshTokenRedisHash>
}