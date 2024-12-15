package com.kamcci.numberbox.restapi.controller.docs

import com.kamcci.numberbox.restapi.annotation.WebMvcUnitTest
import com.kamcci.numberbox.restapi.common.BaseMockMvcTest
import org.junit.jupiter.api.Test
import org.springframework.http.converter.HttpMessageNotReadableException

@WebMvcUnitTest
class MathDocsUsageWriteControllerTest : BaseMockMvcTest() {
    companion object {
        const val PREFIX = "/math/docs/usage"
        const val DOCS_USAGE_CREATE_URL = PREFIX
    }

    @Test
    fun `학습지 제작 기록 추가 - 성공`() {
        // given
        val reqBody = mapOf(
            "contentsIdList" to listOf(1, 2, 3),
            "docsGrade" to "중1",
            "docsTitle" to "소인수 분해",
            "docsSubTitle" to "최대공약수 구하기",
            "docsOwner" to "호랑이 선생님",
        )

        // when
        val resultAction = postRequest(DOCS_USAGE_CREATE_URL, reqBody)

        // then
        assert2xx(resultAction)
    }

    @Test
    fun `학습지 제작 기록 추가 - 실패(필수값 누락)`() {
        // given
        val reqBody = mapOf(
            "contentsIdList" to null,
            "docsGrade" to null,
            "docsTitle" to null,
            "docsSubTitle" to null,
            "docsOwner" to null,
        )

        // when
        val resultAction = postRequest(DOCS_USAGE_CREATE_URL, reqBody)

        // then
        assert4xx(resultAction)
        assertException(resultAction, HttpMessageNotReadableException::class)
    }
}