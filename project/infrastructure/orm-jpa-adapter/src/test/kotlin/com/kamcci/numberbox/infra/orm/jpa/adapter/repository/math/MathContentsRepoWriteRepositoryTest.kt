package com.kamcci.numberbox.infra.orm.jpa.adapter.repository.math

import com.kamcci.numberbox.app.domain.dto.math.MathContentsRepoModifyDto
import com.kamcci.numberbox.infra.orm.jpa.adapter.annotation.TcDBJpaTest
import jakarta.persistence.EntityManager
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import java.util.*

@TcDBJpaTest
class MathContentsRepoWriteRepositoryTest(
    @Autowired
    private val em: EntityManager,
    @Autowired
    private val mathContentsRepoWriteRepository: MathContentsRepoWriteRepository
) {
    @Test
    fun `수학 문제 저장소 저장`() {
        // given
        val memberId = UUID.fromString("16ed5466-cda8-ea4d-9bc7-037cb86fdb20")
        val modifyDto = MathContentsRepoModifyDto(1L, memberId)

        // when
        val isSaved = mathContentsRepoWriteRepository.save(modifyDto)
        em.flush()
        em.clear()

        // then
        assertThat(isSaved).isTrue()
    }

    @Test
    fun `수학 문제 저장소 삭제`() {
        // given
        val memberId = UUID.fromString("10ed5466-cda8-ea4d-9bc7-037cb86fdb20")
        val modifyDto = MathContentsRepoModifyDto(1L, memberId)


        // when
        val executedRowCnt = mathContentsRepoWriteRepository.delete(modifyDto)
        em.flush()
        em.clear()

        // then
        assertThat(executedRowCnt).isGreaterThan(0)
    }
}