package com.kamcci.numberbox.infra.orm.jpa.adapter.repository.member

import com.kamcci.numberbox.app.domain.enumeration.member.VerifyCodeType
import com.kamcci.numberbox.infra.orm.jpa.adapter.annotation.TcDBJpaTest
import com.kamcci.numberbox.infra.orm.jpa.adapter.dummy.member.MemberDummyFactory.NOT_EXIST_MEMBER_EMAIL
import com.kamcci.numberbox.infra.orm.jpa.adapter.dummy.member.MemberVerifyCodeDummyFactory.getMemberVerifyCodeDummyEntity
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

@TcDBJpaTest
class MemberVerifyCodeReadRepositoryTest @Autowired constructor(
    private val memberVerifyCodeReadRepo: MemberVerifyCodeReadRepository
) {
    private val memberVerifyCodeDummyEntity = getMemberVerifyCodeDummyEntity()

    @Test
    fun `countByEmail - 성공`() {
        // given
        val email = memberVerifyCodeDummyEntity.email
        val verifyCodeType = memberVerifyCodeDummyEntity.verifyCodeType

        // when
        val cnt = memberVerifyCodeReadRepo.countByEmailAndCodeType(email, verifyCodeType)

        assertThat(cnt).isPositive()
    }

    @Test
    fun `존재하는 이메일로 조회 - 성공`() {
        // given
        val email = memberVerifyCodeDummyEntity.email
        val verifyCodeType = memberVerifyCodeDummyEntity.verifyCodeType

        // when
        val emailVerifyCodeVo = memberVerifyCodeReadRepo.readByEmailAndCodeType(email, verifyCodeType)

        assertThat(emailVerifyCodeVo).isNotNull
        assertThat(emailVerifyCodeVo!!.verifyCode).isNotNull
    }

    @Test
    fun `미존재 이메일로 조회 - 성공`() {
        // given
        val email = NOT_EXIST_MEMBER_EMAIL
        val verifyCodeType = VerifyCodeType.SignUp

        // when
        val emailVerifyCodeVo =
            memberVerifyCodeReadRepo.readByEmailAndCodeType(email, verifyCodeType)

        assertThat(emailVerifyCodeVo).isNull()
    }
}