package com.kamcci.numberbox.infra.redis.adapter.repository.member

import com.kamcci.numberbox.app.domain.enumeration.member.VerifyCodeType
import com.kamcci.numberbox.infra.redis.adapter.hash.member.MemberVerifyCodeRedisHash
import com.kamcci.numberbox.infra.redis.anntation.TCRedisTest
import org.assertj.core.api.AssertionsForClassTypes
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import java.time.LocalDateTime
import kotlin.jvm.optionals.getOrNull

@TCRedisTest
class MemberVerifyCodeRedisRepositoryTest @Autowired constructor(
    private val memberVerifyCodeRedisRepository: MemberVerifyCodeRedisRepository
) {
    private val email = "test111@test.com"
    private val notExistEmail = "notExistEmail@test.com"
    private val codeType = VerifyCodeType.SignUp
    private val verifyCode = "any"
    private val tryCnt = 0
    private val sysCreateTime: LocalDateTime = LocalDateTime.now()
    private val redisHash = MemberVerifyCodeRedisHash(email, codeType, verifyCode, tryCnt, sysCreateTime)
    private val notExistRedisHash = MemberVerifyCodeRedisHash(notExistEmail, codeType, verifyCode, tryCnt)

    @BeforeEach
    fun init() {
        memberVerifyCodeRedisRepository.save(redisHash)
    }

    @Test
    fun `인증코드 조회 - 존재`() {
        // when
        val resultRedisHash = memberVerifyCodeRedisRepository.findById(email).get()

        // then
        AssertionsForClassTypes.assertThat(redisHash.email).isEqualTo(resultRedisHash.email)
        AssertionsForClassTypes.assertThat(redisHash.codeType).isEqualTo(resultRedisHash.codeType)
        AssertionsForClassTypes.assertThat(redisHash.verifyCode).isEqualTo(resultRedisHash.verifyCode)
        AssertionsForClassTypes.assertThat(redisHash.tryCnt).isEqualTo(resultRedisHash.tryCnt)
        AssertionsForClassTypes.assertThat(redisHash.sysCreateTime).isEqualTo(resultRedisHash.sysCreateTime)
    }

    @Test
    fun `인증코드 조회 - 미존재`() {
        // when
        val resultRedisHash = memberVerifyCodeRedisRepository.findById(notExistEmail).getOrNull()

        // then
        AssertionsForClassTypes.assertThat(resultRedisHash).isNull()
    }

}