package com.kamcci.numberbox.restapi.controller.members

import com.fasterxml.jackson.databind.ObjectMapper
import com.kamcci.modules.auth.control.service.TokenResponseService
import com.kamcci.numberbox.app.domain.dto.member.MemberSignUpDto
import com.kamcci.numberbox.app.domain.dto.member.MemberSignUpResultVo
import com.kamcci.numberbox.app.domain.dto.member.MemberSignUpResultVo.SignUpResultMSg.EXPIRED_MSG
import com.kamcci.numberbox.app.usecase.member.MemberSignupUseCase
import com.kamcci.numberbox.restapi.mapper.member.MemberSignupMapper
import org.junit.jupiter.api.Test
import org.mockito.Mockito.`when`
import org.mockito.kotlin.any
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import java.util.*


@ActiveProfiles("rest-api")
@WebMvcTest(MemberSignupController::class)
class MemberSignupControllerTest {
    @MockBean
    lateinit var tokenResponseService: TokenResponseService

    @MockBean
    lateinit var signupUseCase: MemberSignupUseCase

    @MockBean
    lateinit var signupMapper: MemberSignupMapper

    @Autowired
    lateinit var mockMvc: MockMvc

    private val objectMapper: ObjectMapper = ObjectMapper()

    companion object {
        private const val CREATE_VERIFY_CODE_URL = "/public/createEmailIdCode"
        private const val SIGNUP_URL = "/public/signUp"

        // 정상 케이스 테스트 케이스
        private const val EMAIL = "test@test.com"
        private const val PW = "abcd1234!"
        private const val VERIFY_CODE = "3e0c5f0e-3e12-488c-be48-88fdb92c2dd0"
        private const val NAME = "홍길동"
        private const val PHONE = "01012341234"
        private const val BIRTH = "650123"

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

    @Test
    fun `유효하지 않은 이메일로 이메일 검증코드 생성 요청- 실패`() {
        // given
        val reqBody = mapOf("email" to "testtest.com")

        //when
        val resultAction = requestJsonPost(CREATE_VERIFY_CODE_URL, reqBody)

        // then
        resultAction.andExpect(MockMvcResultMatchers.status().is4xxClientError)
    }

    @Test
    fun `회원가입 요청 개인정보 미포함 - 성공`() {
        // given
        val reqBody = mapOf(
            "email" to EMAIL,
            "password" to PW,
            "confirmPassword" to PW,
            "emailVerifyCode" to VERIFY_CODE,
        )

        val memberSignUpDto = MemberSignUpDto(EMAIL, PW, UUID.fromString(VERIFY_CODE))
        val memberPrivateSignupDto = null
        val mockMemberSignUpResultVo = MemberSignUpResultVo(false, EXPIRED_MSG)
        `when`(signupMapper.toDto(any())).thenReturn(memberSignUpDto)
        `when`(signupMapper.toPrivateDto(any())).thenReturn(memberPrivateSignupDto)
        `when`(signupUseCase.signup(memberSignUpDto, memberPrivateSignupDto))
            .thenReturn(mockMemberSignUpResultVo)

        val resultAction = requestJsonPost(SIGNUP_URL, reqBody)

        // then
        resultAction.andExpect(MockMvcResultMatchers.status().is2xxSuccessful)
    }

    @Test
    fun `회원가입 요청 개인정보 포함 - 성공`() {
        // given
        val reqBody = mapOf(
            "email" to EMAIL,
            "password" to PW,
            "confirmPassword" to PW,
            "emailVerifyCode" to VERIFY_CODE,
            "privateInfo" to
                    mapOf(
                        "userName" to NAME,
                        "phoneNumber" to PHONE,
                        "birth" to BIRTH,
                    )
        )
        // mocking
        val memberSignUpDto = MemberSignUpDto(EMAIL, PW, UUID.fromString(VERIFY_CODE))
        val memberPrivateSignupDto = null
        val mockMemberSignUpResultVo = MemberSignUpResultVo(false, EXPIRED_MSG)
        `when`(signupMapper.toDto(any())).thenReturn(memberSignUpDto)
        `when`(signupMapper.toPrivateDto(any())).thenReturn(memberPrivateSignupDto)
        `when`(signupUseCase.signup(memberSignUpDto, memberPrivateSignupDto))
            .thenReturn(mockMemberSignUpResultVo)

        //when
        val resultAction = requestJsonPost(SIGNUP_URL, reqBody)

        // then
        resultAction.andExpect(MockMvcResultMatchers.status().is2xxSuccessful)
    }

    @Test
    fun `유효하지 않은 이메일로 회원가입 요청- 실패`() {
        // given
        val reqBody = mapOf(
            "email" to "testtest.com",
            "password" to PW,
            "confirmPassword" to PW,
            "emailVerifyCode" to VERIFY_CODE,
        )

        //when
        val resultAction = requestJsonPost(SIGNUP_URL, reqBody)

        // then
        resultAction.andExpect(MockMvcResultMatchers.status().is4xxClientError)
    }

    @Test
    fun `유효하지 않은 비밀번호로 회원가입 요청 - 실패`() {
        // given
        val reqBody = mapOf(
            "email" to EMAIL,
            "password" to "abcd12",
            "confirmPassword" to PW,
            "emailVerifyCode" to VERIFY_CODE,
        )

        //when
        val resultAction = requestJsonPost(SIGNUP_URL, reqBody)

        // then
        resultAction.andExpect(MockMvcResultMatchers.status().is4xxClientError)
    }

    @Test
    fun `비밀번호 불일치 회원가입 요청 - 실패`() {
        // given
        val reqBody = mapOf(
            "email" to EMAIL,
            "password" to PW,
            "confirmPassword" to "1234abcd!",
            "emailVerifyCode" to VERIFY_CODE,
        )

        //when
        val resultAction = requestJsonPost(SIGNUP_URL, reqBody)

        // then
        resultAction.andExpect(MockMvcResultMatchers.status().is4xxClientError)
    }


    @Test
    fun `이름은 최소 두글자 이상 최대 17글자 이하 - 실패`() {
        // given
        val reqBody = mapOf(
            "email" to EMAIL,
            "password" to PW,
            "confirmPassword" to PW,
            "emailVerifyCode" to VERIFY_CODE,
            "privateInfo" to
                    mapOf(
                        "userName" to "홍",
                        "phoneNumber" to PHONE,
                        "birth" to BIRTH,
                    )
        )

        //when
        val resultAction = requestJsonPost(SIGNUP_URL, reqBody)

        // then
        resultAction.andExpect(MockMvcResultMatchers.status().is4xxClientError)
    }

    @Test
    fun `휴대폰 번호는 하이픈(-) 없이 숫자만 10글자에서 11글자 - 실패`() {
        // given
        val reqBody = mapOf(
            "email" to EMAIL,
            "password" to PW,
            "confirmPassword" to PW,
            "emailVerifyCode" to VERIFY_CODE,
            "privateInfo" to
                    mapOf(
                        "userName" to NAME,
                        "phoneNumber" to "010-1234-5678",
                        "birth" to BIRTH,
                    )
        )

        //when
        val resultAction = requestJsonPost(SIGNUP_URL, reqBody)

        // then
        resultAction.andExpect(MockMvcResultMatchers.status().is4xxClientError)
    }

    @Test
    fun `생년월일은 6글자 ex) 930123 - 실패`() {
        // given
        val reqBody = mapOf(
            "email" to EMAIL,
            "password" to PW,
            "confirmPassword" to PW,
            "emailVerifyCode" to VERIFY_CODE,
            "privateInfo" to
                    mapOf(
                        "userName" to NAME,
                        "phoneNumber" to PHONE,
                        "birth" to "1960-12-31",
                    )
        )

        //when
        val resultAction = requestJsonPost(SIGNUP_URL, reqBody)

        // then
        resultAction.andExpect(MockMvcResultMatchers.status().is4xxClientError)
    }
}