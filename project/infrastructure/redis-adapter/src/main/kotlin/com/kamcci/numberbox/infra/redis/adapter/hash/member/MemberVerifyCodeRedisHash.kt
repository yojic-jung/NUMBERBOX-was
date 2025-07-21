package com.kamcci.numberbox.infra.redis.adapter.hash.member

import com.kamcci.numberbox.app.domain.enumeration.member.VerifyCodeType
import com.kamcci.numberbox.app.service.member.MemberVerifyCodeReadService.Companion.EMAIL_CODE_EXPIRE_TIME
import com.kamcci.numberbox.infra.redis.adapter.common.CacheNames
import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash

@RedisHash(CacheNames.MEMBER_EMAIL_VERIFY_CODE, timeToLive = EMAIL_CODE_EXPIRE_TIME)
data class MemberVerifyCodeRedisHash(
    @Id
    val email: String,
    val codeType: VerifyCodeType,
    val verifyCode: String,
    val tryCnt: Int,
)