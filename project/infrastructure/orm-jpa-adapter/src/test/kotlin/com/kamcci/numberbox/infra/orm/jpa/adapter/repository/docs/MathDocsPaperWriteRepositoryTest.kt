package com.kamcci.numberbox.infra.orm.jpa.adapter.repository.docs

import com.kamcci.numberbox.app.domain.dto.docs.MathDocsPaperCreateDto
import com.kamcci.numberbox.app.domain.dto.docs.MathDocsPaperUpdtDto
import com.kamcci.numberbox.app.domain.enumeration.docs.DocsStatusType
import com.kamcci.numberbox.infra.orm.jpa.adapter.annotation.TcDBJpaTest
import jakarta.persistence.EntityManager
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import java.util.*

@TcDBJpaTest
class MathDocsPaperWriteRepositoryTest(
    @Autowired private val em: EntityManager,
    @Autowired private val mathDocsPaperWriteRepository: MathDocsPaperWriteRepository
) {
    @Test
    fun `학습지 생성`() {
        // given
        val memberId = UUID.fromString("10ed5466-cda8-ea4d-9bc7-037cb86fdb20")
        val createDto = MathDocsPaperCreateDto(listOf(1L, 2L, 3L), "", "", "", "", DocsStatusType.None)

        // when
        val id = mathDocsPaperWriteRepository.create(memberId, createDto)
        em.flush()
        em.clear()

        // then
        assertThat(id).isGreaterThan(0)
    }

    @Test
    fun `학습지 수정`() {
        // given
        val memberId = UUID.fromString("10ed5466-cda8-ea4d-9bc7-037cb86fdb20")
        val updateDto = MathDocsPaperUpdtDto(1L, listOf(1L, 2L, 3L), "", "", "", "", DocsStatusType.None)

        // when
        val id = mathDocsPaperWriteRepository.update(memberId, updateDto)
        em.flush()
        em.clear()

        // then
        assertThat(id).isGreaterThan(0)
    }

    @Test
    fun `학습지 상태 변경`() {
        // given
        val memberId = UUID.fromString("10ed5466-cda8-ea4d-9bc7-037cb86fdb20")

        // when
        val executeRowCnt =
            mathDocsPaperWriteRepository.updateDocsSttsByIdAndMemberId(1L, memberId, DocsStatusType.None)
        em.flush()
        em.clear()

        // then
        assertThat(executeRowCnt).isGreaterThan(0)
    }

    @Test
    fun `학습지 삭제`() {
        // given
        val memberId = UUID.fromString("10ed5466-cda8-ea4d-9bc7-037cb86fdb20")

        // when
        val executeRowCnt = mathDocsPaperWriteRepository.delete(1L, memberId)
        em.flush()
        em.clear()

        // then
        assertThat(executeRowCnt).isGreaterThan(0)
    }

    @Test
    fun `사용자의 모든 학습지 삭제`() {
        // given
        val memberId = UUID.fromString("10ed5466-cda8-ea4d-9bc7-037cb86fdb20")

        // when
        val executeRowCnt = mathDocsPaperWriteRepository.delete(memberId)
        em.flush()
        em.clear()

        // then
        assertThat(executeRowCnt).isGreaterThan(0)
    }
}