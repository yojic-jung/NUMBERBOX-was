package com.kamcci.numberbox.infra.orm.jpa.adapter.repository.member

import com.kamcci.numberbox.infra.orm.jpa.adapter.annotation.TcDBJpaTest
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
    private val memberRepositorySupport: MemberRepositorySupport
) {
    companion object {
        const val EXIST_ID = "10ed5466-cda8-ea4d-9bc7-037cb86fdb20"
    }

    @Test
    fun `로그인 실패 횟수 변경`() {
        // given
        val memberId = UUID.fromString(EXIST_ID)

        // when
        val executedRowCnt = memberRepositorySupport.updateSuccessUser(memberId, 0, 1)
        em.flush()
        em.clear()

        // then
        assertThat(executedRowCnt).isGreaterThan(0)
    }
}