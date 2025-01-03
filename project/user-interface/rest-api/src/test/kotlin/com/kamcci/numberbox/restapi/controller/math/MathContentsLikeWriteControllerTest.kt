package com.kamcci.numberbox.restapi.controller.math

import com.kamcci.numberbox.app.usecase.math.MathContentsReadCase
import com.kamcci.numberbox.restapi.annotation.WebMvcUnitTest
import com.kamcci.numberbox.restapi.common.BaseMockMvcTest
import com.kamcci.numberbox.restapi.validation.math.ContentsCheck.Companion.NOT_EXIST_CONTENT
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.kotlin.any
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.method.annotation.HandlerMethodValidationException

@WebMvcUnitTest
class MathContentsLikeWriteControllerTest(
    @Autowired
    private val mathContentsReadCase: MathContentsReadCase
) : BaseMockMvcTest() {
    companion object {
        const val PREFIX = "/math/repo/content"
    }

    @Test
    fun `문제 저장소 - 성공`() {
        // given
        val reqBody = mapOf("contentsId" to "1")
        Mockito.`when`(mathContentsReadCase.existById(any())).thenReturn(true)

        // when
        val resultAction = postRequest(PREFIX, reqBody)

        // then
        assert2xx(resultAction)
    }

    @Test
    fun `문제 저장소 - 실패`() {
        // given
        val reqBody = mapOf("contentsId" to "1")
        Mockito.`when`(mathContentsReadCase.existById(any())).thenReturn(false)

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
        Mockito.`when`(mathContentsReadCase.existById(any())).thenReturn(true)

        // when
        val resultAction = delRequest("$PREFIX/$contentsId", reqBody)

        // then
        assert2xx(resultAction)
    }

    @Test
    fun `문제 저장소 취소 - 실패`() {
        // given
        val contentsId = 1
        Mockito.`when`(mathContentsReadCase.existById(any())).thenReturn(false)

        // when
        val resultAction = delRequest("$PREFIX/$contentsId")

        // then
        assert4xx(resultAction)
        assertException(resultAction, HandlerMethodValidationException::class)
    }

}