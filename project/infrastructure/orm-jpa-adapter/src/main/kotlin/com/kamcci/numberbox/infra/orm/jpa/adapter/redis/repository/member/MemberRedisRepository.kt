package com.kamcci.numberbox.infra.orm.jpa.adapter.redis.repository.member

import com.kamcci.numberbox.infra.orm.jpa.adapter.redis.hash.member.MemberRedisHash
import org.springframework.data.repository.CrudRepository
import java.util.*

interface MemberRedisRepository : CrudRepository<MemberRedisHash, UUID> {
    fun findByEmail(email: String): MemberRedisHash?

    override fun deleteById(id: UUID)

    override fun deleteAllById(ids: Iterable<UUID>)
}