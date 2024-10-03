package com.kamcci.numberbox.infra.orm.adapter.member

import com.kamcci.numberbox.app.domain.enumeration.member.VerifyCodeType
import com.kamcci.numberbox.infra.orm.annotation.TcDBJpaTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

@TcDBJpaTest
class MemberVerifyCodeReadOrmAdapterTest(
    @Autowired
    private val memberVerifyCodeReadRepo: MemberVerifyCodeReadOrmAdapter
) {
    @Test
    fun `countByEmail - 성공`() {
        val cnt = memberVerifyCodeReadRepo.countByEmailAndCodeType("dywlr@test.com", VerifyCodeType.SignUp)

        assertThat(cnt).isGreaterThan(0)
    }

    @Test
    fun `존재하는 이메일로 조회 - 성공`() {
        val emailVerifyCodeVo = memberVerifyCodeReadRepo.findByEmailAndCodeType("dywlr@test.com", VerifyCodeType.SignUp)

        assertThat(emailVerifyCodeVo).isNotNull
        assertThat(emailVerifyCodeVo!!.verifyCode).isNotNull
    }

    @Test
    fun `미존재 이메일로 조회 - 성공`() {
        val emailVerifyCodeVo =
            memberVerifyCodeReadRepo.findByEmailAndCodeType("nonExsit@test.com", VerifyCodeType.SignUp)

        assertThat(emailVerifyCodeVo).isNull()
    }
}