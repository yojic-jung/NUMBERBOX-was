package com.kamcci.numberbox.restapi.controller.members

import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@ActiveProfiles("rest-api")
@WebMvcTest(MemberFindController::class)
class MemberFindControllerTest {
    @Autowired
    lateinit var mockMvc: MockMvc

    private val objectMapper: ObjectMapper = ObjectMapper()

    companion object {
        private const val CREATE_VERIFY_CODE_URL = "/public/createEmailIdCode"

        // 정상 케이스 테스트 케이스
        private const val EMAIL = "test@test.com"
    }

    // json POST 요청
    private fun requestJsonPost(url: String, reqBody: Map<String, Any>) =
        mockMvc
            .perform(
                MockMvcRequestBuilders.post(url)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(reqBody))
            )

    @Test
    fun `이메일 검증 코드 생성 요청 - 성공`() {
        // given
        val reqBody = mapOf("email" to EMAIL)

        //when
        val resultAction = requestJsonPost(CREATE_VERIFY_CODE_URL, reqBody)

        // then
        resultAction.andExpect(MockMvcResultMatchers.status().is2xxSuccessful)

    }
}