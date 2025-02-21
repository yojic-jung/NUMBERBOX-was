package com.kamcci.numberbox.restapi.controller.math

import com.kamcci.numberbox.restapi.annotation.WebMvcUnitTest
import com.kamcci.numberbox.restapi.common.BaseMockMvcTest
import com.kamcci.numberbox.restapi.sample.math.MathContentsSampleData.getMathConIpsiSrcCreateRequest
import com.kamcci.numberbox.restapi.sample.math.MathContentsSampleData.getMathConSimilarSrcCreateRequest
import org.junit.jupiter.api.Test

@WebMvcUnitTest
class ManagerContentsWriteControllerTest : BaseMockMvcTest() {
    companion object {
        const val PREFIX = "/manger/math/content"
        const val INHOUSE_CREATE_URL = "$PREFIX/in-house"
        const val IPSI_CREATE_URL = "$PREFIX/ipsi"
    }

    @Test
    fun `자체제작 문제 등록 - 성공`() {
        // given
        val createReq = getMathConSimilarSrcCreateRequest()

        // when
        val resultAction = postRequest(INHOUSE_CREATE_URL, createReq)

        // then
        assert2xx(resultAction)
    }

    @Test
    fun `입시 문제 등록 - 성공`() {
        // given
        val createReq = getMathConIpsiSrcCreateRequest()

        // when
        val resultAction = postRequest(IPSI_CREATE_URL, createReq)

        // then
        assert2xx(resultAction)
    }
}