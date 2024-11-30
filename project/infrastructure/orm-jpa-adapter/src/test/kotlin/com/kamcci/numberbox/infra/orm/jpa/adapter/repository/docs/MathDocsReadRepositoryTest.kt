package com.kamcci.numberbox.infra.orm.jpa.adapter.repository.docs

import com.kamcci.numberbox.app.domain.enumeration.math.ContentsClassifyType
import com.kamcci.numberbox.infra.orm.jpa.adapter.annotation.TcDBJpaTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

@TcDBJpaTest
class MathDocsReadRepositoryTest(
    @Autowired private val mathDocsReadRepository: MathDocsReadRepository
) {
    @Test
    fun `단원 유형별 문제수`() {
        // given
        val unitIdAndTypeId = listOf("22003,1")
        val contentsType = ContentsClassifyType.InHouse
        val quesLevel = listOf(2)

        // when
        val cntGroupByUnit = mathDocsReadRepository.countGroupByUnitAndType(unitIdAndTypeId, contentsType, quesLevel)

        // then
        assertThat(cntGroupByUnit.get(0)).isGreaterThan(0)
    }

    @Test
    fun `자체제작 학습지 제작 - 조회`() {
        // given
        val unitIdAndTypeId = listOf("1-22003")
        val quesLevel = listOf(2)
        val countByType = 5
        val limit = 10

        // when
        val mathDocsVoList =
            mathDocsReadRepository.readAllInHouseDocsVoBy(unitIdAndTypeId, quesLevel, countByType, limit)

        // then
        assertThat(mathDocsVoList.size).isGreaterThan(0)
    }

    @Test
    fun `수학문제 id로 학습지 제작`() {
        // given
        val idList = listOf(1L)

        // when
        val mathDocsList = mathDocsReadRepository.readDocsByContentsIdList(idList)

        // then
        assertThat(mathDocsList.size).isGreaterThan(0)
    }

    @Test
    fun `입시수학 학습지 제작 - 조회`() {

    }

    @Test
    fun `학습지 추가 문제 - 조회`() {

    }
}