package com.kamcci.numberbox.restapi.controller.resource

import com.kamcci.numberbox.app.usecase.resource.MathResourceReadCase
import com.kamcci.numberbox.restapi.annotation.WebMvcUnitTest
import com.kamcci.numberbox.restapi.common.BaseMockMvcTest
import com.kamcci.numberbox.restapi.dummy.resource.MathResourceFixture.getMathResourceDetailVoList
import com.kamcci.numberbox.restapi.dummy.resource.MathResourceFixture.getMathResourceVoList
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.kotlin.any
import org.springframework.beans.factory.annotation.Autowired

@WebMvcUnitTest
class MathResourceReadControllerTest(
    @Autowired
    private val mathResourceReadCase: MathResourceReadCase,
) : BaseMockMvcTest() {
    companion object {
        const val PREFIX = "/math/resource"
        const val MY_URL = "$PREFIX/my"
    }

    @Test
    fun `카테고리 id로 조회 - 성공`() {
        // given
        val mainCateId = 1
        val reqBody = mapOf(
            "pageNum" to "0",
            "pageVolume" to "100",
        )
        Mockito.`when`(mathResourceReadCase.readByMainCateId(any(), any())).thenReturn(getMathResourceVoList())

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
        Mockito.`when`(mathResourceReadCase.readByMemberId(any(), any())).thenReturn(getMathResourceDetailVoList())

        // when
        val resultAction = getRequest(MY_URL, reqBody)

        // then
        assert2xx(resultAction)
    }

}