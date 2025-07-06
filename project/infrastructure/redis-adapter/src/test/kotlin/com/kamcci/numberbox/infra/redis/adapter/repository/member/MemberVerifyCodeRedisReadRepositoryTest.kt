package com.kamcci.numberbox.infra.redis.adapter.repository.member

import com.kamcci.numberbox.app.domain.dto.member.MemberVerifyCodeSaveDto
import com.kamcci.numberbox.app.domain.enumeration.member.VerifyCodeType
import com.kamcci.numberbox.infra.redis.anntation.TCRedisTest
import org.assertj.core.api.AssertionsForClassTypes.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

@TCRedisTest
class MemberVerifyCodeRedisReadRepositoryTest @Autowired constructor(
    private val memberVerifyCodeRedisWriteRepository: MemberVerifyCodeRedisWriteRepository,
    private val memberVerifyCodeRedisReadRepository: MemberVerifyCodeRedisReadRepository
) {

    private val noneEmail = "none@test.com"
    private val email = "test@test.com"
    private val verifyCode = "any"
    private val memberVerifyCodeSaveDto = MemberVerifyCodeSaveDto(
        email = "test@test.com",
        codeType = VerifyCodeType.SignUp,
        verifyCode = verifyCode
    )

    @BeforeEach
    fun init() {
        memberVerifyCodeRedisWriteRepository.save(memberVerifyCodeSaveDto)
    }

    @Test
    fun `인증 코드 조회 - 성공`() {
        val verifyCodeDto = memberVerifyCodeRedisReadRepository.readByEmail(email)

        assertThat(verifyCodeDto!!.verifyCode).isEqualTo(verifyCode)
    }

    @Test
    fun `인증 코드 조회 null - 성공`() {
        val verifyCodeDto = memberVerifyCodeRedisReadRepository.readByEmail(noneEmail)

        assertThat(verifyCodeDto).isNull()
    }
}