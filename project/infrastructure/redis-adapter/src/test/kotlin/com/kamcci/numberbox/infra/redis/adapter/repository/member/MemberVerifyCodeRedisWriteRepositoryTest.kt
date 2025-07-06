package com.kamcci.numberbox.infra.redis.adapter.repository.member

import com.kamcci.numberbox.app.domain.dto.member.MemberVerifyCodeSaveDto
import com.kamcci.numberbox.app.domain.enumeration.member.VerifyCodeType
import com.kamcci.numberbox.infra.redis.anntation.TCRedisTest
import org.assertj.core.api.AssertionsForClassTypes.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

@TCRedisTest
class MemberVerifyCodeRedisWriteRepositoryTest @Autowired constructor(
    private val memberVerifyCodeRedisWriteRepository: MemberVerifyCodeRedisWriteRepository
) {

    @Test
    fun `인증코드 저장 - 성공`() {
        // given
        val memberVerifyCodeSaveDto = MemberVerifyCodeSaveDto(
            email = "test@test.com",
            codeType = VerifyCodeType.SignUp,
            verifyCode = "any"
        )

        // when
        val isSaved = memberVerifyCodeRedisWriteRepository.save(memberVerifyCodeSaveDto)

        // then
        assertThat(isSaved).isTrue()
    }


}