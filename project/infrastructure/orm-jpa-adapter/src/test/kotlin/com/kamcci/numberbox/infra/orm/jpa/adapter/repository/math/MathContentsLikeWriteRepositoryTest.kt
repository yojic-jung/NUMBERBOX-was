package com.kamcci.numberbox.infra.orm.jpa.adapter.repository.math

import com.kamcci.numberbox.app.domain.dto.math.MathContentsLikeModifyDto
import com.kamcci.numberbox.infra.orm.jpa.adapter.annotation.TcDBJpaTest
import com.kamcci.numberbox.infra.orm.jpa.adapter.dummy.math.MathConLikeDummyFactory.getMathConLikeDummyEntity4Del
import com.kamcci.numberbox.infra.orm.jpa.adapter.dummy.member.MemberDummyFactory.getMemberDummyEntity
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

@TcDBJpaTest
class MathContentsLikeWriteRepositoryTest(
    @Autowired
    private val mathContentsLikeWriteRepository: MathContentsLikeWriteRepository
) {

    @Test
    fun `좋아요 정보 저장`() {
        // given
        val anyContentsId = 111L
        val existMemberId = getMemberDummyEntity().memberId
        val modifyDto = MathContentsLikeModifyDto(anyContentsId, existMemberId)

        // when
        val isSaved = mathContentsLikeWriteRepository.save(modifyDto)

        // then
        assertThat(isSaved).isTrue()
    }

    @Test
    fun `좋아요 정보 삭제`() {
        // given
        val dummyEntity = getMathConLikeDummyEntity4Del()
        val modifyDto = MathContentsLikeModifyDto(dummyEntity.contentsId, dummyEntity.memberId)

        // when
        val executeRowCnt = mathContentsLikeWriteRepository.delete(modifyDto)

        // then
        assertThat(executeRowCnt).isGreaterThan(0)
    }
}