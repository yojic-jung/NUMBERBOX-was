package com.kamcci.numberbox.infra.orm.jpa.adapter.repository.member

import com.kamcci.numberbox.infra.orm.jpa.adapter.annotation.TcDBJpaTest
import com.kamcci.numberbox.infra.orm.jpa.adapter.dummy.member.MemberDummyFactory.getMemberDummyEntity
import com.kamcci.numberbox.infra.orm.jpa.adapter.entity.member.MemberEntity
import jakarta.persistence.EntityManager
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

@TcDBJpaTest
class MemberJpaRepositorySupportTest @Autowired constructor(
    private val em: EntityManager,
    private val memberRepo: MemberRepositorySupport
) {

    @Test
    fun `로그인 시간 업데이트 - 성공`() {
        // given
        val memberId = getMemberDummyEntity().memberId
        val failCount = 0
        val humanStatus = 0 // 일반계정

        // when
        memberRepo.updateSuccessUser(memberId, failCount, humanStatus)
        em.flush()
        em.clear()

        // then
        val memberEntity = em.find(MemberEntity::class.java, memberId)
        assertThat(memberEntity.failCount).isEqualTo(failCount)
        assertThat(memberEntity.humanStatus).isEqualTo(humanStatus)
    }
}