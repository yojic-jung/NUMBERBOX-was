package com.kamcci.numberbox.app.service.docs

import com.kamcci.numberbox.app.domain.dto.docs.MathDocsAdditionalReadDto
import com.kamcci.numberbox.app.domain.dto.docs.MathDocsReadDto
import com.kamcci.numberbox.app.domain.enumeration.math.ContentsClassifyType
import com.kamcci.numberbox.app.service.dummy.MathDocsDummyData.getMathIpsiDocsReadDto
import com.kamcci.numberbox.app.service.stub.port.orm.cs.MockMathDocsReadOrmPort
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class MathDocsReadServiceTest {
    private val mathDocsReadService = MathDocsReadService(MockMathDocsReadOrmPort())

    @Test
    fun `학습지 생성 - 성공`() {
        // given
        for (quesLevel in 1..5) {
            val unitIdAndTypeId = "21001-1,21001-2"
            val count = 10
            val readDto = MathDocsReadDto(unitIdAndTypeId, quesLevel, count)


            // when
            val docsList = mathDocsReadService.makeDocs(readDto)

            // then
            assertThat(docsList.size).isGreaterThan(0)
        }
    }

    @Test
    fun `학습지 생성(전부 메인 문제) - 성공`() {
        // given
        val unitIdAndTypeId = "21001-1,21001-2"
        val quesLevel = 1
        val count = 1
        val readDto = MathDocsReadDto(unitIdAndTypeId, quesLevel, count)


        // when
        val docsList = mathDocsReadService.makeDocs(readDto)

        // then
        assertThat(docsList.size).isGreaterThan(0)
    }

    @Test
    fun `학습지 id로 조회 - 성공`() {
        // given
        val mockMathDocsReadOrmPort = MockMathDocsReadOrmPort()
        val mathDocsReadService = MathDocsReadService(mockMathDocsReadOrmPort)
        val contentsIdList = listOf(1L, 2L)

        // when
        mathDocsReadService.readDocsByDocsPaperId(contentsIdList)

        // then
        assertThat(mockMathDocsReadOrmPort.executeCnt).isEqualTo(1)
    }

    @Test
    fun `입시 학습지 조회 - 성공`() {
        // given
        val mockMathDocsReadOrmPort = MockMathDocsReadOrmPort()
        val mathDocsReadService = MathDocsReadService(mockMathDocsReadOrmPort)
        val readDto = getMathIpsiDocsReadDto()

        // when
        mathDocsReadService.readIpsiDocs(readDto)

        // then
        assertThat(mockMathDocsReadOrmPort.executeCnt).isEqualTo(1)
    }

    @Test
    fun `학습지 추가 컨텐츠 조회 - 성공`() {
        // given
        val mockMathDocsReadOrmPort = MockMathDocsReadOrmPort()
        val mathDocsReadService = MathDocsReadService(mockMathDocsReadOrmPort)
        val readDto = MathDocsAdditionalReadDto(21001, 1, ContentsClassifyType.UserCustom)

        // when
        mathDocsReadService.readAdditionalContents(readDto)

        // then
        assertThat(mockMathDocsReadOrmPort.executeCnt).isEqualTo(1)
    }
}