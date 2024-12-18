package com.kamcci.numberbox.restapi.controller

import com.kamcci.numberbox.restapi.annotation.WebMvcUnitTest
import com.kamcci.numberbox.restapi.common.BaseMockMvcTest
import org.junit.jupiter.api.Test
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders

@WebMvcUnitTest
class HealthCheckTest : BaseMockMvcTest() {
    companion object {
        const val HEALTH_CHECK = "/public/health"
    }

    @Test
    fun `헬스 체크 - 성공`() {
        val resultAction = mockMvc.perform(MockMvcRequestBuilders.head(HEALTH_CHECK))

        assert2xx(resultAction)
    }
}