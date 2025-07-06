package com.kamcci.numberbox.infra.redis.adapter.repository.member

import com.kamcci.numberbox.app.domain.dto.member.MemberVerifyCodeSaveDto
import com.kamcci.numberbox.app.port.orm.member.MemberVerifyCodeWriteOrmPort
import com.kamcci.numberbox.infra.redis.adapter.hash.member.MemberVerifyCodeRedisHash
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
class MemberVerifyCodeRedisWriteRepository(
    private val memberVerifyCodeRedisRepository: MemberVerifyCodeRedisRepository
) : MemberVerifyCodeWriteOrmPort {
    override fun save(memberVerifyCodeSaveDto: MemberVerifyCodeSaveDto): Boolean {
        val saveEntity = MemberVerifyCodeRedisHash(
            email = memberVerifyCodeSaveDto.email,
            codeType = memberVerifyCodeSaveDto.codeType,
            verifyCode = memberVerifyCodeSaveDto.verifyCode,
            tryCnt = 0,
            sysCreateTime = LocalDateTime.now()
        )
        memberVerifyCodeRedisRepository.save(saveEntity)
        return true
    }
}