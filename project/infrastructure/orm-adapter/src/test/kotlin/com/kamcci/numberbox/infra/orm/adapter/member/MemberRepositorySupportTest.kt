package com.kamcci.numberbox.infra.orm.adapter.member

import com.kamcci.numberbox.infra.orm.annotation.TcDBJpaTest
import com.kamcci.numberbox.infra.orm.entity.member.MemberEntity
import jakarta.persistence.EntityManager
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import java.util.*

@TcDBJpaTest
class MemberRepositorySupportTest(
    @Autowired
    private val em: EntityManager,
    @Autowired
    private val memberRepo: MemberRepositorySupport
) {
    companion object {
        const val MEMBER_ID = "10ed5466-cda8-ea4d-9bc7-037cb86fdb20"
    }

    @Test
    fun `로그인 시간 업데이트 - 성공`() {
        // given
        val memberId = UUID.fromString(MEMBER_ID)
        val failCount = 0
        val humanStatus = 0

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