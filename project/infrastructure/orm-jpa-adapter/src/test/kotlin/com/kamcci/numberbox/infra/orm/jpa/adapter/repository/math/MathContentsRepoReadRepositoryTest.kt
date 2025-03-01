package com.kamcci.numberbox.infra.orm.jpa.adapter.repository.math

import com.kamcci.numberbox.infra.orm.jpa.adapter.annotation.TcDBJpaTest
import com.kamcci.numberbox.infra.orm.jpa.adapter.dummy.math.MathConRepoDummyFactory.NOT_EXIST_CONTENTS_ID
import com.kamcci.numberbox.infra.orm.jpa.adapter.dummy.math.MathConRepoDummyFactory.getMathConRepoDummyEntity
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

@TcDBJpaTest
class MathContentsRepoReadRepositoryTest @Autowired constructor(
    private val mathContentsRepoReadRepository: MathContentsRepoReadRepository
) {
    private val mathConRepoDummyEntity = getMathConRepoDummyEntity()

    @Test
    fun `사용자의 저장소 내역 조회`() {
        // when
        val contentsIdList = mathContentsRepoReadRepository.readContentsIdByMemberId(mathConRepoDummyEntity.memberId)

        // then
        assertThat(contentsIdList.size).isPositive()
    }

    @Test
    fun `저장소 존재여부 확인 - 존재`() {
        // when
        val isExist =
            mathContentsRepoReadRepository.existByContentsIdAndMemberId(
                mathConRepoDummyEntity.contentsId,
                mathConRepoDummyEntity.memberId
            )

        // then
        assertThat(isExist).isTrue()
    }

    @Test
    fun `저장소 존재여부 확인 - 미존재`() {
        // given
        val contentsId = NOT_EXIST_CONTENTS_ID
        val memberId = mathConRepoDummyEntity.memberId

        // when
        val isExist = mathContentsRepoReadRepository.existByContentsIdAndMemberId(contentsId, memberId)

        // then
        assertThat(isExist).isFalse()
    }
}