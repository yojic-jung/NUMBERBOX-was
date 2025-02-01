package com.kamcci.numberbox.restapi.controller.math

import com.kamcci.numberbox.restapi.annotation.WebMvcUnitTest
import com.kamcci.numberbox.restapi.common.BaseMockMvcTest
import com.kamcci.numberbox.restapi.validation.math.ContentsCheck.Companion.NOT_EXIST_CONTENT
import org.junit.jupiter.api.Test
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.method.annotation.HandlerMethodValidationException

@WebMvcUnitTest
class MathContentsLikeWriteControllerTest : BaseMockMvcTest() {
    companion object {
        const val PREFIX = "/math/like/content"
    }

    @Test
    fun `문제 저장소 - 성공`() {
        // given
        val reqBody = mapOf("contentsId" to "1")

        // when
        val resultAction = postRequest(PREFIX, reqBody)

        // then
        assert2xx(resultAction)
    }

    @Test
    fun `문제 저장소 - 실패`() {
        // given
        val reqBody = mapOf("contentsId" to "2")

        // when
        val resultAction = postRequest(PREFIX, reqBody)

        // then
        assert4xx(resultAction)
        assertException(resultAction, MethodArgumentNotValidException::class)
        assertExMsg(resultAction, NOT_EXIST_CONTENT)
    }

    @Test
    fun `문제 저장소 취소 - 성공`() {
        // given
        val contentsId = 1
        val reqBody = mapOf("contentsId" to contentsId)

        // when
        val resultAction = delRequest("$PREFIX/$contentsId", reqBody)

        // then
        assert2xx(resultAction)
    }

    @Test
    fun `문제 저장소 취소 - 실패`() {
        // given
        val contentsId = 2

        // when
        val resultAction = delRequest("$PREFIX/$contentsId")

        // then
        assert4xx(resultAction)
        assertException(resultAction, HandlerMethodValidationException::class)
    }

}