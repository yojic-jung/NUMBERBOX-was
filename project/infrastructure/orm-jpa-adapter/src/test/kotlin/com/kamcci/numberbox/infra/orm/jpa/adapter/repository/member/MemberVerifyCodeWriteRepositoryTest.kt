package com.kamcci.numberbox.infra.orm.jpa.adapter.repository.member

import com.kamcci.numberbox.app.domain.dto.member.MemberVerifyCodeSaveDto
import com.kamcci.numberbox.app.domain.enumeration.member.VerifyCodeType
import com.kamcci.numberbox.infra.orm.jpa.adapter.annotation.TcDBJpaTest
import com.kamcci.numberbox.infra.orm.jpa.adapter.dummy.member.MemberVerifyCodeDummyFactory.getMemberVerifyCodeDummyEntity
import com.kamcci.numberbox.infra.orm.jpa.adapter.entity.member.MemberVerifyCodeEntity
import jakarta.persistence.EntityManager
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

@TcDBJpaTest
class MemberVerifyCodeWriteRepositoryTest @Autowired constructor(
    private val entityManager: EntityManager,
    private val verifyCodeModifyRepoImpl: MemberVerifyCodeWriteRepository
) {
    companion object {
        // 테스트 데이터
        const val ANY_EMAIL = "test1234535@test.com"
        const val ANY_VERIFY_CODE = "testtestcom"
    }

    @Test
    fun `MemberEmailVerifyCodeEntity 영속화 - 성공`() {

        // when
        val saveDto = MemberVerifyCodeSaveDto(ANY_EMAIL, VerifyCodeType.SignUp, ANY_VERIFY_CODE)
        verifyCodeModifyRepoImpl.save(saveDto)
        entityManager.flush()
        entityManager.clear()

        // then
        val saveEntity = entityManager.find(MemberVerifyCodeEntity::class.java, ANY_EMAIL)
        assertThat(saveEntity.email).isEqualTo(ANY_EMAIL)
        assertThat(saveEntity.verifyCode).isEqualTo(ANY_VERIFY_CODE)
    }

    @Test
    fun `MemberEmailVerifyCodeEntity 수정 - 성공`() {
        // given
        val dummyEntity = getMemberVerifyCodeDummyEntity()
        val email = dummyEntity.email

        // when
        val saveDto = MemberVerifyCodeSaveDto(email, VerifyCodeType.SignUp, ANY_VERIFY_CODE)
        verifyCodeModifyRepoImpl.save(saveDto)
        entityManager.flush()
        entityManager.clear()

        // then
        val saveEntity = entityManager.find(MemberVerifyCodeEntity::class.java, email)
        assertThat(saveEntity.email).isEqualTo(email)
        assertThat(saveEntity.verifyCode).isEqualTo(ANY_VERIFY_CODE)
    }
}