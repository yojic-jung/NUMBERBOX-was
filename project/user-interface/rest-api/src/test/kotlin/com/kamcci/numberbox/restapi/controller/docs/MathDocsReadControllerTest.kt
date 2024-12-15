package com.kamcci.numberbox.restapi.controller.docs

import com.kamcci.numberbox.app.domain.dto.docs.MathDocsReadDto
import com.kamcci.numberbox.app.domain.dto.docs.MathIpsiDocsReadDto.Companion.ILLEGAL_IPSI_MONTH
import com.kamcci.numberbox.app.domain.dto.docs.MathIpsiDocsReadDto.Companion.ILLEGAL_QUES_COUNT
import com.kamcci.numberbox.app.domain.dto.docs.MathIpsiDocsReadDto.Companion.ILLEGAL_QUES_LEVEL
import com.kamcci.numberbox.app.domain.dto.docs.MathIpsiDocsReadDto.Companion.ILLEGAL_WRONG_RATIO
import com.kamcci.numberbox.app.domain.enumeration.math.ContentsClassifyType
import com.kamcci.numberbox.app.domain.exception.BusinessValidException
import com.kamcci.numberbox.app.usecase.docs.MathDocsPaperReadCase
import com.kamcci.numberbox.app.usecase.docs.MathDocsReadCase
import com.kamcci.numberbox.restapi.annotation.WebMvcUnitTest
import com.kamcci.numberbox.restapi.common.BaseMockMvcTest
import com.kamcci.numberbox.restapi.dummy.math.MathDocsFixture.getMathDocsPaperVo
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.kotlin.any
import org.springframework.beans.BeanInstantiationException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.MethodArgumentNotValidException

@WebMvcUnitTest
class MathDocsReadControllerTest @Autowired constructor(
    private val mathDocsPaperReadCase: MathDocsPaperReadCase,
    private val mathDocsReadCase: MathDocsReadCase
) : BaseMockMvcTest() {
    companion object {
        const val PREFIX = "/math/docs"

        // 자체제작 문제 조회
        const val IN_HOUSE_DOCS = "$PREFIX/in-house"

        // 입시 문제 조회
        const val IPSI_DOCS = "$PREFIX/ipsi"

        // 추가 문제 조회
        const val ADDITIONALLY_DOCS = "$PREFIX/additional"

        // 문제 번호로 조회
        const val DOCS_BY_ID = "$PREFIX/"

        // 나의 학습지 내역
        const val MY_DOCS = "$PREFIX/my"
    }

    @Test
    fun `자체제작 문제 조회 - 성공`() {
        // given
        val reqBody = mapOf(
            "unitIdAndTypeId" to "22001-1",
            "quesLevel" to "1",
            "count" to "100",
        )

        // when
        val resultAction = getRequest(IN_HOUSE_DOCS, reqBody)

        // then
        assert2xx(resultAction)
    }

    @Test
    fun `자체제작 문제 조회 - 실패(난이도)`() {
        // given
        val reqBody = mapOf(
            "unitIdAndTypeId" to "22001-1",
            "quesLevel" to "0",
            "count" to "100",
        )

        // when
        val resultAction = getRequest(IN_HOUSE_DOCS, reqBody)

        // then
        assert4xx(resultAction)
        assertException(resultAction, BeanInstantiationException::class)
        assertExMsg(resultAction, MathDocsReadDto.ILLEGAL_QUES_LEVEL)
    }

    @Test
    fun `자체제작 문제 조회 - 실패(문제수)`() {
        // given
        val reqBody = mapOf(
            "unitIdAndTypeId" to "22001-1",
            "quesLevel" to "1",
            "count" to "0",
        )

        // when
        val resultAction = getRequest(IN_HOUSE_DOCS, reqBody)

        // then
        assert4xx(resultAction)
        assertException(resultAction, BeanInstantiationException::class)
        assertExMsg(resultAction, MathDocsReadDto.ILLEGAL_QUES_COUNT)
    }

    @Test
    fun `입시 문제 조회 - 성공`() {
        // given
        val reqBody = mapOf(
            "unitIdAndTypeId" to "22001",
            "quesLevel" to "3,4,5",
            "wrongRatioMin" to "0",
            "wrongRatioMax" to "100",
            "ipsiYearStrt" to "2013",
            "ipsiYearEnd" to "2024",
            "ipsiMonth" to "6,9,11",
            "count" to "100",
        )

        // when
        val resultAction = getRequest(IPSI_DOCS, reqBody)

        // then
        assert2xx(resultAction)
    }

    @Test
    fun `입시 문제 조회 - 실패`() {
        // given
        val expectedMsg =
            listOf(ILLEGAL_QUES_LEVEL, ILLEGAL_WRONG_RATIO, ILLEGAL_WRONG_RATIO, ILLEGAL_IPSI_MONTH, ILLEGAL_QUES_COUNT)
        for (idx in 0..4) {
            val reqBody = mapOf(
                "unitIdAndTypeId" to "22001",
                "quesLevel" to if (idx == 0) "2" else "3,4,5",
                "wrongRatioMin" to if (idx == 1) "-1" else "0",
                "wrongRatioMax" to if (idx == 2) "101" else "100",
                "ipsiYearStrt" to "2013",
                "ipsiYearEnd" to "2024",
                "ipsiMonth" to if (idx == 3) "3" else "6,9,11",
                "count" to if (idx == 4) "1" else "100",
            )

            // when
            val resultAction = getRequest(IPSI_DOCS, reqBody)

            // then
            assert4xx(resultAction)
            assertException(resultAction, BeanInstantiationException::class)
            assertExMsg(resultAction, expectedMsg[idx])
        }
    }

    @Test
    fun `추가 문제 조회 - 성공`() {
        // given
        for (contentsClassifyType in ContentsClassifyType.entries) {
            val reqBody = mapOf(
                "unitId" to "22001",
                "typeId" to "1",
                "contentsClassifyType" to contentsClassifyType.name,
            )

            // when
            val resultAction = getRequest(ADDITIONALLY_DOCS, reqBody)

            // then
            assert2xx(resultAction)
        }
    }

    @Test
    fun `추가 문제 조회 - 실패(미존재 컨텐츠 타입)`() {
        // given
        val reqBody = mapOf(
            "unitId" to "22001",
            "typeId" to "1",
            "contentsClassifyType" to "nothing",
        )

        // when
        val resultAction = getRequest(ADDITIONALLY_DOCS, reqBody)

        // then
        assert4xx(resultAction)
        assertException(resultAction, MethodArgumentNotValidException::class)
    }

    @Test
    fun `나의 학습지 조회 - 성공`() {
        // given
        val docsId = 1L
        val mathDocsPaperVo = getMathDocsPaperVo()
        Mockito.`when`(mathDocsPaperReadCase.readByIdAndMemberId(any(), any())).thenReturn(mathDocsPaperVo)
        Mockito.`when`(mathDocsReadCase.readDocsByDocsPaperId(any())).thenReturn(null)


        // when
        val resultAction = getRequest("$DOCS_BY_ID/$docsId")

        // then
        assert2xx(resultAction)
    }

    @Test
    fun `나의 학습지 조회 - 실퍠(나의 학습지 아님)`() {
        // given
        val docsId = 1L

        // when
        val resultAction = getRequest("$DOCS_BY_ID/$docsId")

        // then
        assert4xx(resultAction)
        assertException(resultAction, BusinessValidException::class)
        assertExMsg(resultAction, MathDocsReadController.NOT_MY_DOCS)
    }

    @Test
    fun `나의 학습지 목록 조회 - 성공`() {
        // given
        val pageReq = mapOf(
            "pageNum" to "0",
            "pageVolume" to "100"
        )

        // when
        val resultAction = getRequest(MY_DOCS, pageReq)

        // then
        assert2xx(resultAction)
    }
}