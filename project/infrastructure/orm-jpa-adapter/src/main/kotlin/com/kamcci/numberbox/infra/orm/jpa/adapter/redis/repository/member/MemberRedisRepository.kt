package com.kamcci.numberbox.infra.orm.jpa.adapter.redis.repository.member

import com.kamcci.numberbox.infra.orm.jpa.adapter.redis.hash.member.MemberRedisHash
import org.springframework.data.repository.CrudRepository
import java.util.*

interface MemberRedisRepository : CrudRepository<MemberRedisHash, UUID> {
    override fun findById(id: UUID): Optional<MemberRedisHash>

    fun findByEmail(email: String): Optional<MemberRedisHash>

    override fun deleteById(id: UUID)

    fun deleteByEmail(email: String)

    fun deleteAllById(id: List<UUID>)
}