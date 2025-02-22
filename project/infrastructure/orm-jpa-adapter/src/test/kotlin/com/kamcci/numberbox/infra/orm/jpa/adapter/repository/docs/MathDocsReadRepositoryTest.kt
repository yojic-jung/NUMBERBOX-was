package com.kamcci.numberbox.infra.orm.jpa.adapter.repository.docs

import com.kamcci.numberbox.app.domain.dto.docs.MathDocsAdditionalReadDto
import com.kamcci.numberbox.app.domain.dto.docs.MathIpsiDocsReadDto
import com.kamcci.numberbox.app.domain.enumeration.math.ContentsClassifyType
import com.kamcci.numberbox.infra.orm.jpa.adapter.annotation.TcDBJpaTest
import com.kamcci.numberbox.infra.orm.jpa.adapter.dummy.MathContentsEntityDummy.INHOUSE_CONTENTS_ID
import com.kamcci.numberbox.infra.orm.jpa.adapter.dummy.MathContentsEntityDummy.getInHouseContentsEntity
import com.kamcci.numberbox.infra.orm.jpa.adapter.dummy.MathContentsEntityDummy.getIpsiContentsEntity
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

@TcDBJpaTest
class MathDocsReadRepositoryTest @Autowired constructor(
    private val mathDocsReadRepository: MathDocsReadRepository
) {
    @Test
    fun `단원 유형별 문제수`() {
        // given
        val existEntityInfo = getInHouseContentsEntity()
        val unitIdAndTypeId = listOf("${existEntityInfo.unitId},${existEntityInfo.typeId}")
        val contentsType = existEntityInfo.contentsClassify
        val quesLevel = listOf(existEntityInfo.quesLevel)

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
        val existEntity = getInHouseContentsEntity()
        val unitIdAndTypeId = listOf("${existEntity.typeId}-${existEntity.unitId}")
        val quesLevel = listOf(existEntity.quesLevel)
        val countByType = 5 // 유형별 문제수
        val pageVolume = 10

        // when
        val mathDocsVoList =
            mathDocsReadRepository.readAllInHouseDocsVoBy(unitIdAndTypeId, quesLevel, countByType, pageVolume)

        // then
        assertThat(mathDocsVoList).isNotEmpty
    }

    @Test
    fun `수학문제 id로 학습지 제작`() {
        // given
        val idList = listOf(INHOUSE_CONTENTS_ID)

        // when
        val mathDocsList = mathDocsReadRepository.readDocsByContentsIdList(idList)

        // then
        assertThat(mathDocsList).isNotEmpty
    }

    @Test
    fun `입시수학 학습지 제작 - 조회`() {
        // given
        val existIpsiDto = getIpsiContentsEntity()
        val readDto = MathIpsiDocsReadDto(
            existIpsiDto.map { "${it.typeId}-${it.unitId}" },
            existIpsiDto.map { it.quesLevel },
            0,
            100,
            2000,
            2024,
            listOf(6, 9, 11),
            100
        )

        // when
        val mathDocsList = mathDocsReadRepository.readAllIpsiDocsVoBy(readDto)

        // then
        assertThat(mathDocsList).isNotEmpty
    }

    @Test
    fun `학습지 추가 문제 - 조회`() {
        // given
        val existEntity = getInHouseContentsEntity()
        val readDto =
            MathDocsAdditionalReadDto(existEntity.unitId, existEntity.typeId, ContentsClassifyType.InHouse)

        // when
        val mathDocsList = mathDocsReadRepository.readAdditionalContents(readDto)

        // then
        assertThat(mathDocsList).isNotEmpty
    }
}