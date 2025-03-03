package com.kamcci.numberbox.infra.orm.jpa.adapter.repository.docs

import com.kamcci.numberbox.app.domain.dto.docs.MathDocsAdditionalReadDto
import com.kamcci.numberbox.app.domain.dto.docs.MathIpsiDocsReadDto
import com.kamcci.numberbox.app.domain.enumeration.math.ContentsClassifyType
import com.kamcci.numberbox.infra.orm.jpa.adapter.annotation.TcDBJpaTest
import com.kamcci.numberbox.infra.orm.jpa.adapter.dummy.math.MathContentsDummyFactory.getInHouseContentsDummyEntity
import com.kamcci.numberbox.infra.orm.jpa.adapter.dummy.math.MathContentsDummyFactory.getIpsiContentsDummyEntityList
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

@TcDBJpaTest
class MathDocsReadRepositoryTest @Autowired constructor(
    private val mathDocsReadRepository: MathDocsReadRepository
) {
    private val inHouseContentDummyEntity = getInHouseContentsDummyEntity()
    private val ipsiContentDummyEntityList = getIpsiContentsDummyEntityList()

    @Test
    fun `단원 유형별 문제수`() {
        // given
        val unitIdAndTypeId = listOf("${inHouseContentDummyEntity.unitId},${inHouseContentDummyEntity.typeId}")
        val contentsType = inHouseContentDummyEntity.contentsClassifyType
        val quesLevel = listOf(inHouseContentDummyEntity.quesLevel)

        // when
        val cntGroupByUnit = mathDocsReadRepository.countGroupByUnitAndType(unitIdAndTypeId, contentsType, quesLevel)

        // then
        cntGroupByUnit.forEach { size ->
            assertThat(size).isPositive()
        }
    }

    @Test
    fun `자체제작 학습지 제작 - 조회`() {
        // given
        val unitIdAndTypeId = listOf("${inHouseContentDummyEntity.typeId}-${inHouseContentDummyEntity.unitId}")
        val quesLevel = listOf(inHouseContentDummyEntity.quesLevel)
        val countByType = 5 // 유형별 문제수
        val pageVolume = 10

        // when
        val mathDocsVoList =
            mathDocsReadRepository.readAllInHouseDocsVoBy(unitIdAndTypeId, quesLevel, countByType, pageVolume)

        // then
        mathDocsVoList.forEach { mathDocsVo ->
            assertThat(quesLevel).contains(mathDocsVo.quesLevel)
        }
    }

    @Test
    fun `수학문제 id로 학습지 제작`() {
        // given
        val idList = listOf(inHouseContentDummyEntity.contentsId)

        // when
        val mathDocsList = mathDocsReadRepository.readDocsByContentsIdList(idList)

        // then
        assertThat(mathDocsList[0].contentsId).isEqualTo(inHouseContentDummyEntity.contentsId)
    }

    @Test
    fun `입시수학 학습지 제작 - 조회`() {
        // given - 전체 범위 검색 조건 설정
        val readDto = getIpsiReadDto4AllRanges()

        // when
        val mathDocsList = mathDocsReadRepository.readAllIpsiDocsVoBy(readDto)

        // then
        assertThat(mathDocsList).isNotEmpty
    }

    // 오답률 및 출제 연도 모든 범위로 검색 조건 설정
    private fun getIpsiReadDto4AllRanges(): MathIpsiDocsReadDto {
        return MathIpsiDocsReadDto(
            ipsiContentDummyEntityList.map { "${it.typeId}-${it.unitId}" },
            ipsiContentDummyEntityList.map { it.quesLevel },
            wrongRatioMin = 0,
            wrongRatioMax = 100,
            ipsiYearStrt = 2000,
            ipsiYearEnd = 2024,
            ipsiMonth = listOf(6, 9, 11),
            count = 100
        )
    }

    @Test
    fun `학습지 추가 문제 - 조회`() {
        // given
        val readDto =
            MathDocsAdditionalReadDto(
                inHouseContentDummyEntity.unitId,
                inHouseContentDummyEntity.typeId,
                ContentsClassifyType.InHouse
            )

        // when
        val mathDocsList = mathDocsReadRepository.readAdditionalContents(readDto)

        // then
        assertThat(mathDocsList).isNotEmpty
    }
}