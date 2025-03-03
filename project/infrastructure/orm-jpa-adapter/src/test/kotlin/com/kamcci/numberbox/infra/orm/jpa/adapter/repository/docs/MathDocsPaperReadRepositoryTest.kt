package com.kamcci.numberbox.infra.orm.jpa.adapter.repository.docs

import com.kamcci.numberbox.app.domain.dto.common.PageRequestImpl
import com.kamcci.numberbox.infra.orm.jpa.adapter.annotation.TcDBJpaTest
import com.kamcci.numberbox.infra.orm.jpa.adapter.dummy.docs.MathDocsPaperDummyFactory.getDocsPaperDummyEntity4Read
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
        val existEntityInfo = getDocsPaperDummyEntity4Read()

        // when
        val docsPaper = mathDocsPaperReadRepository.readByIdAndMemberId(existEntityInfo.id, existEntityInfo.memberId)

        // then
        assertThat(docsPaper?.id).isEqualTo(existEntityInfo.id)
    }

    @Test
    fun `학습지 내역 조회`() {
        // given
        val dummyEntity = getDocsPaperDummyEntity4Read()
        val memberId = dummyEntity.memberId
        val pageReq = PageRequestImpl(pageNum = 0, pageVolume = 10)

        // when
        val docsList = mathDocsPaperReadRepository.readByMemberId(memberId, pageReq)

        // then
        assertThat(docsList[0].id).isEqualTo(dummyEntity.id)
    }

    @Test
    fun `사용자가 제작한 학습지 수 조회`() {
        // when
        val memberId = getDocsPaperDummyEntity4Read().memberId
        val cnt = mathDocsPaperReadRepository.countByMemberId(memberId)

        // then
        assertThat(cnt).isPositive()
    }
}