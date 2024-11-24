package com.kamcci.numberbox.infra.orm.jpa.adapter.repository.member

import com.kamcci.numberbox.infra.orm.jpa.adapter.annotation.TcDBJpaTest
import com.kamcci.numberbox.infra.orm.jpa.adapter.entity.member.MemberRoleEntity
import jakarta.persistence.EntityManager
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import java.util.*

@TcDBJpaTest
class MemberRoleModifyRepositoryTest(
    @Autowired
    private val entityManager: EntityManager,
    @Autowired
    private val memberRoleModifyRepo: MemberRoleWriteRepository
) {
    companion object {
        const val MEMBER_ID = "10ca3122-cda8-ea4d-9bc7-037cb86fdb20"
        const val NONE_EXIST_MEMBER_ID = "12ca3122-cda8-ea4d-9bc7-037cb86fdb20"
    }

    @Test
    fun `멤버 권한 영속화 테스트 - 성공`() {
        // given
        val memberId = UUID.fromString("10ed5466-cda8-ea4d-9bc7-037cb86fdb20")

        // when
        val roleId = memberRoleModifyRepo.saveUserRole(memberId)
        entityManager.flush()
        entityManager.clear()

        // then
        val memberRole = entityManager.find(MemberRoleEntity::class.java, roleId)
        assertThat(memberRole.id).isEqualTo(roleId)
    }

    @Test
    fun `권한 enabled 수정 - 성공`() {
        // given
        val memberId = UUID.fromString(MEMBER_ID)
        val enabled = true

        // when
        val isUpdated = memberRoleModifyRepo.updateEnabledById(memberId, enabled)

        // then
        assertThat(isUpdated).isTrue()
    }

    @Test
    fun `존재하지 않는 계정 권한 enabled 수정 - 실패`() {
        // given
        val memberId = UUID.fromString(NONE_EXIST_MEMBER_ID)
        val enabled = true

        // when
        val isUpdated = memberRoleModifyRepo.updateEnabledById(memberId, enabled)

        // then
        assertThat(isUpdated).isFalse()
    }
}