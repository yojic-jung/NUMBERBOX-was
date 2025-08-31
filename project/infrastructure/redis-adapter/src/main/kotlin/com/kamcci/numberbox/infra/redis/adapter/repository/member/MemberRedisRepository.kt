package com.kamcci.numberbox.infra.redis.adapter.repository.member

import com.kamcci.numberbox.infra.redis.adapter.hash.member.MemberRedisHash
import org.springframework.data.repository.CrudRepository
import java.util.*

interface MemberRedisRepository : CrudRepository<MemberRedisHash, UUID> {
    fun findByEmail(email: String): MemberRedisHash?

    override fun deleteById(id: UUID)

    override fun deleteAllById(ids: Iterable<UUID>)
}