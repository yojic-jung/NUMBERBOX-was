package com.kamcci.numberbox.infra.orm.jpa.adapter.repository.resource

import com.kamcci.numberbox.app.domain.dto.common.PageRequestImpl
import com.kamcci.numberbox.infra.orm.jpa.adapter.annotation.TcDBJpaTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import java.util.*

@TcDBJpaTest
class MathResourceReadRepositoryTest(
    @Autowired
    private val mathResourceReadRepository: MathResourceReadRepository
) {
    private val memberId = UUID.fromString("10ed5466-cda8-ea4d-9bc7-037cb86fdb20")

    @Test
    fun `대분류 id로 조회`() {
        // given
        val mainCateId = 1
        val pageRequestImpl = PageRequestImpl(0, 10)

        // when
        val resourceList = mathResourceReadRepository.readByMainCateId(mainCateId, pageRequestImpl)

        // then
        assertThat(resourceList.size).isGreaterThan(0)
    }

    @Test
    fun `대분류 id로 전체 카운트 조회`() {
        // given
        val mainCateId = 1

        // when
        val cnt = mathResourceReadRepository.countByMainCateId(mainCateId)

        // then
        assertThat(cnt).isGreaterThan(0)
    }

    @Test
    fun `id로 조회`() {
        // given
        val id = 1L

        // when
        val resource = mathResourceReadRepository.readById(id)

        // then
        assertThat(resource).isNotNull
    }

    @Test
    fun `memberId로 조회`() {
        // given
        val pageRequestImpl = PageRequestImpl(0, 10)

        // when
        val resourceList = mathResourceReadRepository.readByMemberId(memberId, pageRequestImpl)

        // then
        assertThat(resourceList.size).isGreaterThan(0)
    }

    @Test
    fun `memberId로 전체 카운트 조회`() {
        // when
        val cnt = mathResourceReadRepository.countByMemberId(memberId)

        // then
        assertThat(cnt).isGreaterThan(0)
    }

    @Test
    fun `memberId로 학습 자료 파일 조회`() {
        // given
        val id = 1L

        // when
        val resource = mathResourceReadRepository.readFileById(id)

        // then
        assertThat(resource).isNotNull
    }
}