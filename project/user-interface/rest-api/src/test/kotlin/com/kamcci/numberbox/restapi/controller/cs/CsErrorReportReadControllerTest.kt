package com.kamcci.numberbox.restapi.controller.cs

import com.kamcci.numberbox.restapi.annotation.WebMvcUnitTest
import com.kamcci.numberbox.restapi.common.BaseMockMvcTest
import org.junit.jupiter.api.Test

@WebMvcUnitTest
class CsErrorReportReadControllerTest : BaseMockMvcTest() {
    companion object {
        // 고객센터 내 문의 내역
        const val MY_CS_ERROR = "/cs/error/my"
    }


    @Test
    fun `고객센터 내 문의 내역 - 성공`() {
        //when
        val resultAction = getRequest(MY_CS_ERROR)

        // then
        assert2xx(resultAction)
    }

}