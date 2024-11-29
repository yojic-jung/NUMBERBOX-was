package com.kamcci.numberbox.infra.orm.jpa.adapter.repository.math

import com.kamcci.numberbox.app.domain.dto.math.MathContentsLikeModifyDto
import com.kamcci.numberbox.infra.orm.jpa.adapter.annotation.TcDBJpaTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import java.util.*

@TcDBJpaTest
class MathContentsLikeWriteRepositoryTest(
    @Autowired
    private val mathContentsLikeWriteRepository: MathContentsLikeWriteRepository
) {
    private val memberId = UUID.fromString("10ed5466-cda8-ea4d-9bc7-037cb86fdb20")

    @Test
    fun `좋아요 정보 저장`() {
        // given
        val modifyDto = MathContentsLikeModifyDto(1L, memberId)

        // when
        val isSaved = mathContentsLikeWriteRepository.save(modifyDto)

        // then
        assertThat(isSaved).isTrue()
    }

    @Test
    fun `좋아요 정보 삭제`() {
        // given
        val modifyDto = MathContentsLikeModifyDto(1L, memberId)

        // when
        val isDeleted = mathContentsLikeWriteRepository.delete(modifyDto)

        // then
        assertThat(isDeleted).isTrue()
    }
}