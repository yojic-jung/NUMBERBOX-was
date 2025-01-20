package com.kamcci.numberbox.restapi.controller.hwp

import com.kamcci.numberbox.restapi.annotation.WebMvcUnitTest
import com.kamcci.numberbox.restapi.common.BaseMockMvcTest
import org.junit.jupiter.api.Test

@WebMvcUnitTest
class HwpConvertControllerTest : BaseMockMvcTest() {
    companion object {
        const val PREFIX = "/hwp/convert"
        const val JSON_TO_HWP = "$PREFIX/json-to-hwp"
    }

    @Test
    fun `json to hwp 변환 요청 - 성공`() {
        // given
        val reqBody = mapOf(
            "jsonMsg" to "asdfjlalf",
        )

        // when
        val resultAction = postRequest(JSON_TO_HWP, reqBody)

        // then
        assert2xx(resultAction)
    }
}