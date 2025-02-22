package com.kamcci.numberbox.infra.orm.jpa.adapter.repository.docs

import com.kamcci.numberbox.app.domain.dto.common.PageRequestImpl
import com.kamcci.numberbox.infra.orm.jpa.adapter.annotation.TcDBJpaTest
import com.kamcci.numberbox.infra.orm.jpa.adapter.dummy.docs.MathDocsPaperEntityDummy.DOCS_PAPER_MEMBER_ID
import com.kamcci.numberbox.infra.orm.jpa.adapter.dummy.docs.MathDocsPaperEntityDummy.getDocsPaperEntity4Read
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

@TcDBJpaTest
class MathDocsPaperReadRepositoryTest @Autowired constructor(
    private val mathDocsPaperReadRepository: MathDocsPaperReadRepository
) {

    @Test
    fun `학습지 정보 조회`() {
        // given
        val existEntityInfo = getDocsPaperEntity4Read()

        // when
        val docsPaper = mathDocsPaperReadRepository.readByIdAndMemberId(existEntityInfo.id, existEntityInfo.memberId)

        // then
        assertThat(docsPaper).isNotNull
    }

    @Test
    fun `학습지 내역 조회`() {
        // given
        val memberId = DOCS_PAPER_MEMBER_ID
        val pageReq = PageRequestImpl(0, 10)

        // when
        val docsList = mathDocsPaperReadRepository.readByMemberId(memberId, pageReq)

        // then
        assertThat(docsList).isNotEmpty
    }

    @Test
    fun `사용자가 제작한 학습지 수 조회`() {
        // when
        val memberId = DOCS_PAPER_MEMBER_ID
        val cnt = mathDocsPaperReadRepository.countByMemberId(memberId)

        // then
        assertThat(cnt).isPositive()
    }
}