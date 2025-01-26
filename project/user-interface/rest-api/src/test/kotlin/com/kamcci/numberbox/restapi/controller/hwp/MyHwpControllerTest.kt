package com.kamcci.numberbox.restapi.controller.hwp

import com.kamcci.numberbox.restapi.annotation.WebMvcUnitTest
import com.kamcci.numberbox.restapi.common.BaseMockMvcTest
import org.junit.jupiter.api.Test

@WebMvcUnitTest
class MyHwpControllerTest : BaseMockMvcTest() {
    companion object {
        const val PREFIX = "/hwp/contents"
        const val MY_CONTENTS = "$PREFIX/my"
    }

    @Test
    fun `나의 변환 컨텐츠 조회 - 성공`() {
        // when
        val resultAction = getRequest(MY_CONTENTS)

        // then
        assert2xx(resultAction)
    }
}