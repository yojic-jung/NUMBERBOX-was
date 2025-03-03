package com.kamcci.numberbox.restapi.controller.resource

import com.kamcci.numberbox.app.service.constant.MockTestConstant.SUCCESS_ID
import com.kamcci.numberbox.restapi.annotation.WebMvcUnitTest
import com.kamcci.numberbox.restapi.common.BaseMockMvcTest
import org.junit.jupiter.api.Test

@WebMvcUnitTest
class MathResourceReadControllerTest : BaseMockMvcTest() {
    companion object {
        const val PREFIX = "/math/resource"
        const val MY_URL = "$PREFIX/my"
    }

    @Test
    fun `카테고리 id로 조회 - 성공`() {
        // given
        val mainCateId = SUCCESS_ID
        val reqBody = mapOf(
            "pageNum" to "0",
            "pageVolume" to "100",
        )

        // when
        val resultAction = getRequest("$PREFIX/$mainCateId", reqBody)

        // then
        assert2xx(resultAction)
    }

    @Test
    fun `카테고리 id로 조회(카운트 함수 실행) - 성공`() {
        // given
        val mainCateId = SUCCESS_ID
        val reqBody = mapOf(
            "pageNum" to "0",
            "pageVolume" to "2",
        )

        // when
        val resultAction = getRequest("$PREFIX/$mainCateId", reqBody)

        // then
        assert2xx(resultAction)
    }

    @Test
    fun `나의 학습 자료 조회 - 성공`() {
        // given
        val reqBody = mapOf(
            "pageNum" to "0",
            "pageVolume" to "100",
        )

        // when
        val resultAction = getRequest(MY_URL, reqBody)

        // then
        assert2xx(resultAction)
    }

    @Test
    fun `나의 학습 자료 조회(카운트 실행) - 성공`() {
        // given
        val reqBody = mapOf(
            "pageNum" to "0",
            "pageVolume" to "2",
        )

        // when
        val resultAction = getRequest(MY_URL, reqBody)

        // then
        assert2xx(resultAction)
    }
}