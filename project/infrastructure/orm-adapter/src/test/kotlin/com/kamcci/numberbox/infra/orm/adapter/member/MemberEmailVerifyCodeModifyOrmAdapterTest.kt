package com.kamcci.numberbox.infra.orm.adapter.member

import com.kamcci.numberbox.app.domain.dto.member.MemberEmailVerifyCodeSaveDto
import com.kamcci.numberbox.infra.orm.annotation.TcDBJpaTest
import com.kamcci.numberbox.infra.orm.entity.member.MemberEmailVerifyCodeEntity
import jakarta.persistence.EntityManager
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

@TcDBJpaTest
class MemberEmailVerifyCodeModifyOrmAdapterTest(
    @Autowired
    private val entityManager: EntityManager,
    @Autowired
    private val emailVerifyCodeModifyRepoImpl: MemberEmailVerifyCodeSaveOrmAdapter
) {
    companion object {
        const val EMAIL = "test1234535@test.com"
        const val VERIFY_CODE = "testtestcom"
    }

    @Test
    fun `MemberEmailVerifyCodeEntity 영속화 - 성공`() {
        val saveDto = MemberEmailVerifyCodeSaveDto(EMAIL, VERIFY_CODE)
        emailVerifyCodeModifyRepoImpl.save(saveDto)
        entityManager.flush()
        entityManager.clear()

        val saveEntity = entityManager.find(MemberEmailVerifyCodeEntity::class.java, EMAIL)

        assertThat(saveEntity.email).isEqualTo(EMAIL)
        assertThat(saveEntity.verifyCode).isEqualTo(VERIFY_CODE)
    }
}