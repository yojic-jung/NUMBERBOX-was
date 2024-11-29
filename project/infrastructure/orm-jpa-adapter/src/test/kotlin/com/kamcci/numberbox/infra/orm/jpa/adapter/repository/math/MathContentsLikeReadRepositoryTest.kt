package com.kamcci.numberbox.infra.orm.jpa.adapter.repository.math

import com.kamcci.numberbox.infra.orm.jpa.adapter.annotation.TcDBJpaTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import java.util.*

@TcDBJpaTest
class MathContentsLikeReadRepositoryTest(
    @Autowired
    private val mathContentsLikeReadRepository: MathContentsLikeReadRepository
) {
    private val memberId = UUID.fromString("10ed5466-cda8-ea4d-9bc7-037cb86fdb20")

    @Test
    fun `memberId로 조회`() {
        // when
        val isExist = mathContentsLikeReadRepository.existByContentsIdAndMemberId(1L, memberId)

        // then
        assertThat(isExist).isTrue()
    }
}