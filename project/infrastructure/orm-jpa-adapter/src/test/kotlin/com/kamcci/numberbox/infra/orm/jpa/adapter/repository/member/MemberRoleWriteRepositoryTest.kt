package com.kamcci.numberbox.infra.orm.jpa.adapter.repository.member

import com.kamcci.numberbox.infra.orm.jpa.adapter.annotation.TcDBJpaTest
import com.kamcci.numberbox.infra.orm.jpa.adapter.dummy.member.MemberDummyFactory.NOT_EXIST_MEMBER_ID
import com.kamcci.numberbox.infra.orm.jpa.adapter.dummy.member.MemberRoleDummyFactory.NOT_EXIST_ROLE_MEMBER
import com.kamcci.numberbox.infra.orm.jpa.adapter.dummy.member.MemberRoleDummyFactory.getMemberRoleDummyEntity4Updt
import com.kamcci.numberbox.infra.orm.jpa.adapter.entity.member.MemberRoleEntity
import jakarta.persistence.EntityManager
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

@TcDBJpaTest
class MemberRoleWriteRepositoryTest @Autowired constructor(
    private val entityManager: EntityManager,
    private val memberRoleModifyRepo: MemberRoleWriteRepository
) {
    @Test
    fun `멤버 권한 영속화 테스트 - 성공`() {
        // given
        val memberId = NOT_EXIST_ROLE_MEMBER

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
        val dummyEntity = getMemberRoleDummyEntity4Updt()
        val memberId = dummyEntity.memberId
        val enabled = true

        // when
        val isUpdated = memberRoleModifyRepo.updateEnabledById(memberId, enabled)

        // then
        assertThat(isUpdated).isTrue()
    }

    @Test
    fun `존재하지 않는 계정 권한 enabled 수정 - 실패`() {
        // given
        val memberId = NOT_EXIST_MEMBER_ID
        val enabled = true

        // when
        val isUpdated = memberRoleModifyRepo.updateEnabledById(memberId, enabled)

        // then
        assertThat(isUpdated).isFalse()
    }
}