package com.kamcci.numberbox.restapi.controller.members

import com.kamcci.numberbox.app.domain.enumeration.member.VerifyCodeType
import com.kammci.numberbox.restapi.annotation.WebMvcUnitTest
import com.kammci.numberbox.restapi.common.BaseMockMvcTest
import org.junit.jupiter.api.Test
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@WebMvcUnitTest
class MemberFindControllerTest : BaseMockMvcTest() {

    companion object {
        private const val CREATE_VERIFY_CODE_URL = "/member/public/verifyCode"
        private const val FIND_EMAIL_URL = "/member/public/findEmail"
        private const val FIND_PASSWD_URL = "/member/public/findPassword"

        // 정상 케이스 테스트 케이스
        private const val EMAIL = "test@test.com"
    }

    @Test
    fun `이메일 검증 코드 생성 요청 - 성공`() {
        // given
        val reqBody = mapOf(
            "email" to EMAIL,
            "codeType" to VerifyCodeType.SignUp
        )

        //when
        val resultAction = requestJsonPost(CREATE_VERIFY_CODE_URL, reqBody)

        // then
        resultAction.andExpect(MockMvcResultMatchers.status().is2xxSuccessful)
    }

    @Test
    fun `유효하지 않은 이메일로 이메일 검증코드 생성 요청- 실패`() {
        // given
        val reqBody = mapOf(
            "email" to "testtest.com",
            "codeType" to VerifyCodeType.SignUp
        )

        //when
        val resultAction = requestJsonPost(CREATE_VERIFY_CODE_URL, reqBody)

        // then
        resultAction.andExpect(MockMvcResultMatchers.status().is4xxClientError)
    }

    @Test
    fun `이메일 찾기 - 성공`() {
        // given
        val reqBody = mapOf(
            "userName" to "이름",
            "phoneNumber" to "01012341234"
        )

        //when
        val resultAction = requestGET(FIND_EMAIL_URL, reqBody)

        // then
        resultAction.andExpect(MockMvcResultMatchers.status().is2xxSuccessful)
    }
}