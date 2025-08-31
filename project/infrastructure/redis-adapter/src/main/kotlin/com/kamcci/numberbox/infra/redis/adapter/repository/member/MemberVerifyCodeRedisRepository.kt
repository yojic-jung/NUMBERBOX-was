package com.kamcci.numberbox.infra.redis.adapter.repository.member

import com.kamcci.numberbox.infra.redis.adapter.hash.member.MemberVerifyCodeRedisHash
import org.springframework.data.repository.CrudRepository
import java.util.*

interface MemberVerifyCodeRedisRepository : CrudRepository<MemberVerifyCodeRedisHash, String> {
    override fun findById(id: String): Optional<MemberVerifyCodeRedisHash>
}