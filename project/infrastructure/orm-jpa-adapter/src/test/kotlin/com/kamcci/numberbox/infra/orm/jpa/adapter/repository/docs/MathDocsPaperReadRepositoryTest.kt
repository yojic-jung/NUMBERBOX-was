package com.kamcci.numberbox.infra.orm.jpa.adapter.repository.docs

import com.kamcci.numberbox.app.domain.dto.common.PageRequestImpl
import com.kamcci.numberbox.infra.orm.jpa.adapter.annotation.TcDBJpaTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import java.util.*

@TcDBJpaTest
class MathDocsPaperReadRepositoryTest(
    @Autowired private val mathDocsPaperReadRepository: MathDocsPaperReadRepository
) {
    private val memberId = UUID.fromString("10ed5466-cda8-ea4d-9bc7-037cb86fdb20")

    @Test
    fun `학습지 정보 조회`() {
        // given
        val id = 1L

        // when
        val docsPaper = mathDocsPaperReadRepository.readByIdAndMemberId(id, memberId)

        // then
        assertThat(docsPaper).isNotNull
    }

    @Test
    fun `학습지 내역 조회`() {
        // given
        val pageReq = PageRequestImpl(0, 10)

        // when
        val docsList = mathDocsPaperReadRepository.readByMemberId(memberId, pageReq)

        // then
        assertThat(docsList.size).isGreaterThan(0)
    }

    @Test
    fun `사용자가 제작한 학습지 수 조회`() {
        // when
        val cnt = mathDocsPaperReadRepository.countByMemberId(memberId)

        // then
        assertThat(cnt).isGreaterThan(0)
    }
}