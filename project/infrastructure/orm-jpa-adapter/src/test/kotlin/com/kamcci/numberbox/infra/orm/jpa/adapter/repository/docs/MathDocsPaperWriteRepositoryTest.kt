package com.kamcci.numberbox.infra.orm.jpa.adapter.repository.docs

import com.kamcci.numberbox.app.domain.enumeration.docs.DocsStatusType
import com.kamcci.numberbox.app.service.sample.MathDocsSampleData.getMathDocsPaperCreateDto
import com.kamcci.numberbox.app.service.sample.MathDocsSampleData.getMathDocsPaperUpdtDto
import com.kamcci.numberbox.infra.orm.jpa.adapter.annotation.TcDBJpaTest
import com.kamcci.numberbox.infra.orm.jpa.adapter.dummy.docs.MathDocsPaperDummyFactory.DOCS_PAPER_MEMBER_ID
import com.kamcci.numberbox.infra.orm.jpa.adapter.dummy.docs.MathDocsPaperDummyFactory.getDocsPaperDummyEntity4AllDel
import com.kamcci.numberbox.infra.orm.jpa.adapter.dummy.docs.MathDocsPaperDummyFactory.getDocsPaperDummyEntity4Del
import com.kamcci.numberbox.infra.orm.jpa.adapter.dummy.docs.MathDocsPaperDummyFactory.getDocsPaperDummyEntity4Updt
import jakarta.persistence.EntityManager
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

@TcDBJpaTest
class MathDocsPaperWriteRepositoryTest @Autowired constructor(
    private val em: EntityManager,
    private val mathDocsPaperWriteRepository: MathDocsPaperWriteRepository
) {
    @Test
    fun `학습지 생성`() {
        // given
        val memberId = DOCS_PAPER_MEMBER_ID
        val createDto = getMathDocsPaperCreateDto()

        // when
        val id = mathDocsPaperWriteRepository.create(memberId, createDto)
        em.flush()
        em.clear()

        // then
        assertThat(id).isPositive()
    }

    @Test
    fun `학습지 수정`() {
        // given
        val existEntityInfo = getDocsPaperDummyEntity4Updt()
        val updateDto = getMathDocsPaperUpdtDto(existEntityInfo.id)

        // when
        val id = mathDocsPaperWriteRepository.update(existEntityInfo.memberId, updateDto)
        em.flush()
        em.clear()

        // then
        assertThat(id).isPositive()
    }

    @Test
    fun `학습지 상태 변경`() {
        // given
        val existEntity4Updt = getDocsPaperDummyEntity4Updt()
        val docsStatus = DocsStatusType.None

        // when
        val executeRowCnt =
            mathDocsPaperWriteRepository.updateDocsSttsByIdAndMemberId(
                existEntity4Updt.id,
                existEntity4Updt.memberId,
                docsStatus
            )
        em.flush()
        em.clear()

        // then
        assertThat(executeRowCnt).isPositive()
    }

    @Test
    fun `학습지 삭제`() {
        // given
        val existEntity4Del = getDocsPaperDummyEntity4Del()

        // when
        val executeRowCnt = mathDocsPaperWriteRepository.delete(existEntity4Del.id, existEntity4Del.memberId)
        em.flush()
        em.clear()

        // then
        assertThat(executeRowCnt).isPositive()
    }

    @Test
    fun `사용자의 모든 학습지 삭제`() {
        // given
        val existEntity4AllDel = getDocsPaperDummyEntity4AllDel()

        // when
        val executeRowCnt = mathDocsPaperWriteRepository.delete(existEntity4AllDel.memberId)
        em.flush()
        em.clear()

        // then
        assertThat(executeRowCnt).isPositive()
    }
}