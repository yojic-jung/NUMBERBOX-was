package com.kamcci.numberbox.infra.orm.jpa.adapter.repository.math

import com.kamcci.numberbox.infra.orm.jpa.adapter.annotation.TcDBJpaTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import java.util.*

@TcDBJpaTest
class MathContentsRepoReadRepositoryTest(
    @Autowired
    private val mathContentsRepoReadRepository: MathContentsRepoReadRepository
) {
    private val memberId = UUID.fromString("10ed5466-cda8-ea4d-9bc7-037cb86fdb20")

    @Test
    fun `사용자의 저장소 내역 조회`() {
        // when
        val contentsIdList = mathContentsRepoReadRepository.readContentsIdByMemberId(memberId)

        // then
        assertThat(contentsIdList.size).isGreaterThan(0)
    }

    @Test
    fun `저장소 존재여부 확인 - 존재`() {
        // given
        val contentsId = 1L

        // when
        val isExist = mathContentsRepoReadRepository.existByContentsIdAndMemberId(contentsId, memberId)

        // then
        assertThat(isExist).isTrue()
    }

    @Test
    fun `저장소 존재여부 확인 - 미존재`() {
        // given
        val contentsId = 99999999L

        // when
        val isExist = mathContentsRepoReadRepository.existByContentsIdAndMemberId(contentsId, memberId)

        // then
        assertThat(isExist).isFalse()
    }
}