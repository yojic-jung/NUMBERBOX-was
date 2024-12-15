package com.kamcci.numberbox.restapi.controller.docs

import com.kamcci.numberbox.app.domain.enumeration.docs.DocsStatusType
import com.kamcci.numberbox.restapi.annotation.WebMvcUnitTest
import com.kamcci.numberbox.restapi.common.BaseMockMvcTest
import org.junit.jupiter.api.Test
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.method.annotation.HandlerMethodValidationException

@WebMvcUnitTest
class MathDocsWriteControllerTest : BaseMockMvcTest() {
    companion object {
        const val PREFIX = "/math/docs"
        const val DOCS_CREATE_URL = PREFIX
        const val DOCS_UPDATE_URL = PREFIX
        const val DOCS_DELETE_URL = PREFIX
    }

    @Test
    fun `학습지 생성 - 성공`() {
        // given
        val reqBody = mapOf(
            "contentsIdList" to listOf(1, 2, 3),
            "docsGrade" to "고1",
            "docsTitle" to "제목",
            "docsSubTitle" to "부제목",
            "docsOwner" to "호랑이 선생님",
            "docsStts" to DocsStatusType.None.name,
        )

        // when
        val resultAction = postRequest(DOCS_CREATE_URL, reqBody)

        // then
        assert2xx(resultAction)
    }

    @Test
    fun `학습지 생성 - 실패(필수값 미기재)`() {
        // given
        val reqBody = mapOf(
            "contentsIdList" to null,
            "docsGrade" to null,
            "docsTitle" to null,
            "docsSubTitle" to null,
            "docsOwner" to null,
            "docsStts" to null,
        )

        // when
        val resultAction = postRequest(DOCS_CREATE_URL, reqBody)

        // then
        assert4xx(resultAction)
        assertException(resultAction, HttpMessageNotReadableException::class)
    }

    @Test
    fun `학습지 수정 - 성공`() {
        // given
        val reqBody = mapOf(
            "id" to 1,
            "contentsIdList" to listOf(1, 2, 3),
            "docsGrade" to "고1",
            "docsTitle" to "제목",
            "docsSubTitle" to "부제목",
            "docsOwner" to "호랑이 선생님",
            "docsStts" to DocsStatusType.None.name,
        )

        // when
        val resultAction = putRequest(DOCS_UPDATE_URL, reqBody)

        // then
        assert2xx(resultAction)
    }

    @Test
    fun `학습지 수정 - 실패(필수값 미기재)`() {
        // given
        val reqBody = mapOf(
            "id" to 1,
            "contentsIdList" to null,
            "docsGrade" to null,
            "docsTitle" to null,
            "docsSubTitle" to null,
            "docsOwner" to null,
            "docsStts" to null,
        )

        // when
        val resultAction = postRequest(DOCS_UPDATE_URL, reqBody)

        // then
        assert4xx(resultAction)
        assertException(resultAction, HttpMessageNotReadableException::class)
    }

    @Test
    fun `학습지 삭제 - 성공`() {
        // given
        val docsId = 1L

        // when
        val resultAction = delRequest("$DOCS_DELETE_URL/$docsId")

        // then
        assert2xx(resultAction)
    }

    @Test
    fun `학습지 삭제 - 실패(필수값 미기재)`() {
        // given
        val docsId = 0L

        // when
        val resultAction = delRequest("$DOCS_DELETE_URL/$docsId")

        // then
        assert4xx(resultAction)
        assertException(resultAction, HandlerMethodValidationException::class)
    }
}