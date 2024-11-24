package com.kamcci.numberbox.infra.orm.jpa.adapter.repository.member

import com.kamcci.numberbox.app.domain.dto.member.MemberVerifyCodeSaveDto
import com.kamcci.numberbox.app.domain.enumeration.member.VerifyCodeType
import com.kamcci.numberbox.infra.orm.jpa.adapter.annotation.TcDBJpaTest
import com.kamcci.numberbox.infra.orm.jpa.adapter.entity.member.MemberVerifyCodeEntity
import jakarta.persistence.EntityManager
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

@TcDBJpaTest
class MemberVerifyCodeWriteRepositoryTest(
    @Autowired
    private val entityManager: EntityManager,
    @Autowired
    private val verifyCodeModifyRepoImpl: MemberVerifyCodeWriteRepository
) {
    companion object {
        const val EMAIL = "test1234535@test.com"
        const val VERIFY_CODE = "testtestcom"
    }

    @Test
    fun `MemberEmailVerifyCodeEntity 영속화 - 성공`() {
        val saveDto = MemberVerifyCodeSaveDto(EMAIL, VerifyCodeType.SignUp, VERIFY_CODE)
        verifyCodeModifyRepoImpl.save(saveDto)
        entityManager.flush()
        entityManager.clear()

        val saveEntity = entityManager.find(MemberVerifyCodeEntity::class.java, EMAIL)

        assertThat(saveEntity.email).isEqualTo(EMAIL)
        assertThat(saveEntity.verifyCode).isEqualTo(VERIFY_CODE)
    }
}