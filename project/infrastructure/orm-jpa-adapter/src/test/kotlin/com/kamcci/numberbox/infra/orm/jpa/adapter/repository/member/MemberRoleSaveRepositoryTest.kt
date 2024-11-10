package com.kamcci.numberbox.infra.orm.jpa.adapter.repository.member

import com.kamcci.numberbox.infra.orm.jpa.adapter.annotation.TcDBJpaTest
import com.kamcci.numberbox.infra.orm.jpa.adapter.entity.member.MemberRoleEntity
import jakarta.persistence.EntityManager
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import java.util.*

@TcDBJpaTest
class MemberRoleSaveRepositoryTest(
    @Autowired
    private val entityManager: EntityManager,
    @Autowired
    private val memberRoleSaveRepositoryImpl: MemberRoleSaveRepository
) {
    @Test
    fun `멤버 권한 영속화 테스트 - 성공`() {
        // given
        val memberId = UUID.fromString("10ed5466-cda8-ea4d-9bc7-037cb86fdb20")

        // when
        val roleId = memberRoleSaveRepositoryImpl.saveUserRole(memberId)
        entityManager.flush()
        entityManager.clear()

        // then
        val memberRole = entityManager.find(MemberRoleEntity::class.java, roleId)
        assertThat(memberRole.id).isEqualTo(roleId)
    }
}