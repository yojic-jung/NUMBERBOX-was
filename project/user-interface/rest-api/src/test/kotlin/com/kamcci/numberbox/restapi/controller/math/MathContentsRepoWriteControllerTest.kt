package com.kamcci.numberbox.restapi.controller.math

import com.kamcci.numberbox.app.service.constant.MockTestConstant.FAIL_ID
import com.kamcci.numberbox.app.service.constant.MockTestConstant.SUCCESS_ID
import com.kamcci.numberbox.restapi.annotation.WebMvcUnitTest
import com.kamcci.numberbox.restapi.common.BaseMockMvcTest
import com.kamcci.numberbox.restapi.validation.math.ContentsCheck
import org.junit.jupiter.api.Test
import org.springframework.web.bind.MethodArgumentNotValidException

@WebMvcUnitTest
class MathContentsRepoWriteControllerTest : BaseMockMvcTest() {
    companion object {
        const val PREFIX = "/math/repo/content"
    }

    @Test
    fun `문제 저장소 - 성공`() {
        // given
        val reqBody = mapOf("contentsId" to SUCCESS_ID)

        // when
        val resultAction = postRequest(PREFIX, reqBody)

        // then
        assert2xx(resultAction)
    }

    @Test
    fun `문제 저장소 - 실패`() {
        // given
        val reqBody = mapOf("contentsId" to FAIL_ID)

        // when
        val resultAction = postRequest(PREFIX, reqBody)

        // then
        assert4xx(resultAction)
        assertException(resultAction, MethodArgumentNotValidException::class)
        assertExMsg(resultAction, ContentsCheck.NOT_EXIST_CONTENT)
    }

    @Test
    fun `문제 저장소 취소 - 성공`() {
        // given
        val contentsId = SUCCESS_ID
        val reqBody = mapOf("contentsId" to contentsId)

        // when
        val resultAction = delRequest("$PREFIX/$contentsId", reqBody)

        // then
        assert2xx(resultAction)
    }
}