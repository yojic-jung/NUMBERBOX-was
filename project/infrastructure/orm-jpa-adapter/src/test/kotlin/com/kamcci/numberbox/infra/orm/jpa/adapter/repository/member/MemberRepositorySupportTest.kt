package com.kamcci.numberbox.infra.orm.jpa.adapter.repository.member

import com.kamcci.numberbox.infra.orm.jpa.adapter.annotation.TcDBJpaTest
import com.kamcci.numberbox.infra.orm.jpa.adapter.dummy.member.MemberDummyFactory.getMemberDummyEntity
import jakarta.persistence.EntityManager
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

@TcDBJpaTest
class MemberRepositorySupportTest @Autowired constructor(
    private val em: EntityManager,
    private val memberRepositorySupport: MemberRepositorySupport
) {
    private val memberDummyEntity = getMemberDummyEntity()

    @Test
    fun `로그인 실패 횟수 변경`() {
        // given
        val memberId = memberDummyEntity.memberId
        val failCount = 0 // 실패 횟수 초기화
        val humanStatus = 1 // 비활성 계정

        // when
        val executedRowCnt = memberRepositorySupport.updateSuccessUser(memberId, failCount, humanStatus)
        em.flush()
        em.clear()

        // then
        assertThat(executedRowCnt).isPositive()
    }
}