package com.kamcci.numberbox.infra.orm.jpa.adapter.repository.member

import com.kamcci.numberbox.infra.orm.jpa.adapter.annotation.TcDBJpaTest
import com.kamcci.numberbox.infra.orm.jpa.adapter.dummy.member.MemberRoleDummyFactory.getMemberRoleDummyEntity
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

@TcDBJpaTest
class MemberRoleReadRepositoryTest @Autowired constructor(
    private val memberRoleReadRepository: MemberRoleReadRepository
) {
    private val memberRoleDummyEntity = getMemberRoleDummyEntity()

    @Test
    fun `사용자 권한 조회 - memberId`() {
        // given
        val memberId = memberRoleDummyEntity.memberId

        // when
        val roleList = memberRoleReadRepository.readRoleByMemberId(memberId)

        // then
        assertThat(roleList.size).isPositive()
    }
}