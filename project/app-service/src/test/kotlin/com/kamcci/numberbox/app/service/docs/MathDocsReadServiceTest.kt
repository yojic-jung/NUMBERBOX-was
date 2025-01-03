package com.kamcci.numberbox.app.service.docs

import com.kamcci.numberbox.app.domain.dto.docs.MathDocsAdditionalReadDto
import com.kamcci.numberbox.app.domain.dto.docs.MathDocsReadDto
import com.kamcci.numberbox.app.domain.enumeration.math.ContentsClassifyType
import com.kamcci.numberbox.app.port.orm.docs.MathDocsReadOrmPort
import com.kamcci.numberbox.app.service.docs.MathDocsFixture.getMathIpsiDocsReadDto
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.kotlin.any
import org.mockito.kotlin.verify

class MathDocsReadServiceTest {
    private val mathDocsReadOrmPort: MathDocsReadOrmPort = Mockito.mock()
    private val mathDocsReadService = MathDocsReadService(mathDocsReadOrmPort)

    @Test
    fun `학습지 생성 - 성공`() {
        // given
        for (quesLevel in 1..5) {
            val unitIdAndTypeId = "21001-1,21001-2"
            val count = 10
            val readDto = MathDocsReadDto(unitIdAndTypeId, quesLevel, count)
            val docsVoList = MathDocsFixture.getMathDocsVoList()

            Mockito.`when`(mathDocsReadOrmPort.countGroupByUnitAndType(any(), any(), any())).thenReturn(listOf(1, 1))
            Mockito.`when`(mathDocsReadOrmPort.readAllInHouseDocsVoBy(any(), any(), any(), any()))
                .thenReturn(docsVoList)

            // when
            val docsList = mathDocsReadService.makeDocs(readDto)

            // then
            assertThat(docsList.size).isEqualTo(docsVoList.size * 2)
        }
    }

    @Test
    fun `학습지 생성(전부 메인 문제) - 성공`() {
        // given
        val unitIdAndTypeId = "21001-1,21001-2"
        val quesLevel = 1
        val count = 1
        val readDto = MathDocsReadDto(unitIdAndTypeId, quesLevel, count)
        val docsVoList = MathDocsFixture.getMathDocsVoList()

        Mockito.`when`(mathDocsReadOrmPort.countGroupByUnitAndType(any(), any(), any())).thenReturn(listOf(1, 1))
        Mockito.`when`(mathDocsReadOrmPort.readAllInHouseDocsVoBy(any(), any(), any(), any())).thenReturn(docsVoList)

        // when
        val docsList = mathDocsReadService.makeDocs(readDto)

        // then
        assertThat(docsList.size).isEqualTo(docsVoList.size)
    }

    @Test
    fun `학습지 id로 조회 - 성공`() {
        // given
        val contentsIdList = listOf(1L, 2L)

        // when
        mathDocsReadService.readDocsByDocsPaperId(contentsIdList)

        // then
        verify(mathDocsReadOrmPort).readDocsByContentsIdList(contentsIdList)
    }

    @Test
    fun `입시 학습지 조회 - 성공`() {
        // given
        val readDto = getMathIpsiDocsReadDto()

        // when
        mathDocsReadService.readIpsiDocs(readDto)

        // then
        verify(mathDocsReadOrmPort).readAllIpsiDocsVoBy(readDto)
    }

    @Test
    fun `학습지 추가 컨텐츠 조회 - 성공`() {
        // given
        val readDto = MathDocsAdditionalReadDto(21001, 1, ContentsClassifyType.UserCustom)

        // when
        mathDocsReadService.readAdditionalContents(readDto)

        // then
        verify(mathDocsReadOrmPort).readAdditionalContents(readDto)
    }
}