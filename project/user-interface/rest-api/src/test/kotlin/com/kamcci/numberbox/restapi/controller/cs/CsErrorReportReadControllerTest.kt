package com.kamcci.numberbox.restapi.controller.cs

import com.kamcci.numberbox.app.usecase.cs.CsErrorReportReadCase
import com.kamcci.numberbox.restapi.annotation.WebMvcUnitTest
import com.kamcci.numberbox.restapi.common.BaseMockMvcTest
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.kotlin.any
import org.springframework.beans.factory.annotation.Autowired

@WebMvcUnitTest
class CsErrorReportReadControllerTest : BaseMockMvcTest() {
    companion object {
        const val MY_CS_ERROR = "/cs/error/my"
    }

    @Autowired
    lateinit var csErrorReportReadCase: CsErrorReportReadCase

    @Test
    fun `고객센터 내 문의 내역 - 성공`() {
        // given
        Mockito.`when`(csErrorReportReadCase.readByMemberId(any())).thenReturn(any())

        //when
        val resultAction = getRequest(MY_CS_ERROR)

        // then
        assert2xx(resultAction)
    }

}