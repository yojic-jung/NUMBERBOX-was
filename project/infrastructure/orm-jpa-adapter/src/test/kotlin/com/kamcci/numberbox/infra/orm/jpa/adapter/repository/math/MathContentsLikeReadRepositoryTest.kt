package com.kamcci.numberbox.infra.orm.jpa.adapter.repository.math

import com.kamcci.numberbox.infra.orm.jpa.adapter.annotation.TcDBJpaTest
import com.kamcci.numberbox.infra.orm.jpa.adapter.dummy.math.MathConLikeDummyFactory.getMathConLikeDummyEntity
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

@TcDBJpaTest
class MathContentsLikeReadRepositoryTest(
    @Autowired
    private val mathContentsLikeReadRepository: MathContentsLikeReadRepository
) {
    private val mathConLikeDummyEntity = getMathConLikeDummyEntity()

    @Test
    fun `readMemberIdListById - 존재`() {
        // given
        val dummyEntity = mathConLikeDummyEntity

        // when
        val memberIdList =
            mathContentsLikeReadRepository.readMemberIdListById(dummyEntity.contentsId)

        // then
        assertThat(memberIdList).isNotEmpty
        assertThat(memberIdList[0]).isEqualTo(dummyEntity.memberId)
    }

    @Test
    fun `contentsId와 memberId로 좋아요 여부 조회 - 존재`() {
        // given
        val dummyEntity = mathConLikeDummyEntity

        // when
        val isExist =
            mathContentsLikeReadRepository.existByContentsIdAndMemberId(dummyEntity.contentsId, dummyEntity.memberId)

        // then
        assertThat(isExist).isTrue()
    }

    @Test
    fun `contentsId와 memberId로 좋아요 여부 조회 - 미존재`() {
        // given
        val dummyEntity = mathConLikeDummyEntity
        val notMyContentsId = dummyEntity.contentsId + 100L

        // when
        val isExist = mathContentsLikeReadRepository.existByContentsIdAndMemberId(notMyContentsId, dummyEntity.memberId)

        // then
        assertThat(isExist).isFalse()
    }

    @Test
    fun `countBy - 존재`() {
        // given
        val dummyEntity = mathConLikeDummyEntity

        // when
        val count =
            mathContentsLikeReadRepository.countBy(dummyEntity.contentsId)

        // then
        assertThat(count).isOne()
    }

    @Test
    fun `countBy - 미존재`() {
        // given
        val notExistId = 0L

        // when
        val count = mathContentsLikeReadRepository.countBy(notExistId)

        // then
        assertThat(count).isZero()
    }
}