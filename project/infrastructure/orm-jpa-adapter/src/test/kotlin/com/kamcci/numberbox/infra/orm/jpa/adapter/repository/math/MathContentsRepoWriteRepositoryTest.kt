package com.kamcci.numberbox.infra.orm.jpa.adapter.repository.math

import com.kamcci.numberbox.app.domain.dto.math.MathContentsRepoModifyDto
import com.kamcci.numberbox.infra.orm.jpa.adapter.annotation.TcDBJpaTest
import com.kamcci.numberbox.infra.orm.jpa.adapter.dummy.math.MathConRepoDummyFactory.NOT_EXIST_CONTENTS_ID
import com.kamcci.numberbox.infra.orm.jpa.adapter.dummy.math.MathConRepoDummyFactory.getMathConRepoDummyEntity
import com.kamcci.numberbox.infra.orm.jpa.adapter.dummy.math.MathConRepoDummyFactory.getMathConRepoDummyEntity4Del
import jakarta.persistence.EntityManager
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

@TcDBJpaTest
class MathContentsRepoWriteRepositoryTest @Autowired constructor(
    private val em: EntityManager,
    private val mathContentsRepoWriteRepository: MathContentsRepoWriteRepository
) {
    private val mathConRepoDummyEntity = getMathConRepoDummyEntity()

    @Test
    fun `수학 문제 저장소 저장`() {
        // given
        val modifyDto = MathContentsRepoModifyDto(NOT_EXIST_CONTENTS_ID, mathConRepoDummyEntity.memberId)

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
        val dummyEntity = getMathConRepoDummyEntity4Del()
        val modifyDto = MathContentsRepoModifyDto(dummyEntity.contentsId, dummyEntity.memberId)

        // when
        val executedRowCnt = mathContentsRepoWriteRepository.delete(modifyDto)
        em.flush()
        em.clear()

        // then
        assertThat(executedRowCnt).isGreaterThan(0)
    }
}