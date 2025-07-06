package com.kamcci.numberbox.infra.redis.adapter.repository.member

import com.kamcci.numberbox.app.domain.vo.member.MemberVerifyCodeVo
import com.kamcci.numberbox.app.port.orm.member.MemberVerifyCodeReadOrmPort
import org.springframework.stereotype.Repository
import kotlin.jvm.optionals.getOrNull

@Repository
class MemberVerifyCodeReadRedisRepository(
    private val memberVerifyCodeRedisRepository: MemberVerifyCodeRedisRepository
) : MemberVerifyCodeReadOrmPort {
    override fun readByEmail(email: String): MemberVerifyCodeVo? {
        val redisHash = memberVerifyCodeRedisRepository.findById(email)
        return redisHash.getOrNull()?.let {
            MemberVerifyCodeVo(it.verifyCode, it.sysCreateTime)
        }
    }
}