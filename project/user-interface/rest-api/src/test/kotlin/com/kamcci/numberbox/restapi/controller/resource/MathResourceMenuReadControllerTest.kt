package com.kamcci.numberbox.restapi.controller.resource

import com.kamcci.numberbox.restapi.annotation.WebMvcUnitTest
import com.kamcci.numberbox.restapi.common.BaseMockMvcTest
import org.junit.jupiter.api.Test

@WebMvcUnitTest
class MathResourceMenuReadControllerTest : BaseMockMvcTest() {
    companion object {
        const val PREFIX = "/public/math/resource/menu"
    }

    @Test
    fun `학습자료 카테고리 조회 - 성공`() {
        val resultAction = getRequest(PREFIX)

        assert2xx(resultAction)
    }
}