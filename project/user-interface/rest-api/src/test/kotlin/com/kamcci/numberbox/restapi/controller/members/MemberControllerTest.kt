package com.kamcci.numberbox.restapi.controller.members

import com.kamcci.numberbox.app.domain.dto.member.MemberSignUpDto
import com.kamcci.numberbox.app.domain.vo.member.MemberSignUpResultVo
import com.kamcci.numberbox.app.domain.vo.member.MemberSignUpResultVo.SignUpResultMSg.SUCCESS_MSG
import com.kamcci.numberbox.app.domain.vo.member.MemberVerifyCodeResultVo
import com.kamcci.numberbox.app.domain.vo.member.MemberVerifyCodeResultVo.VerifyResultMSg.VERIFY_SUCCESS
import com.kamcci.numberbox.app.usecase.member.MemberModifyUseCase
import com.kamcci.numberbox.app.usecase.member.MemberVerifyCodeReadUseCase
import com.kamcci.numberbox.restapi.mapper.member.MemberMapper
import com.kammci.numberbox.restapi.annotation.WebMvcUnitTest
import com.kammci.numberbox.restapi.common.BaseMockMvcTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.mockito.Mockito.`when`
import org.mockito.kotlin.any
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.web.servlet.result.MockMvcResultMatchers


@WebMvcUnitTest
class MemberControllerTest : BaseMockMvcTest() {
    @Autowired
    lateinit var memberModifyMock: MemberModifyUseCase

    @Autowired
    lateinit var memberVerifyCodeReadUseCase: MemberVerifyCodeReadUseCase

    @Autowired
    lateinit var memberMapper: MemberMapper

    companion object {
        private const val SIGNUP_URL = "/member/public/signUp"

        // 정상 케이스 테스트 케이스
        private const val EMAIL = "test@test.com"
        private const val PW = "abcd1234!"
        private const val VERIFY_CODE = "3e0c5f0e-3e12-488c-be48-88fdb92c2dd0"
        private const val NAME = "홍길동"
        private const val PHONE = "01012341234"
        private const val BIRTH = "650123"
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

        val memberSignUpDto = MemberSignUpDto(EMAIL, PW)
        val memberPrivateSignupDto = null
        val verifyCodeRs = MemberVerifyCodeResultVo(true, VERIFY_SUCCESS)
        val mockMemberSignUpResultVo = MemberSignUpResultVo(true, SUCCESS_MSG)

        `when`(memberMapper.toSignupDto(any())).thenReturn(memberSignUpDto)
        `when`(memberMapper.toSignupPrivateDto(any())).thenReturn(memberPrivateSignupDto)
        `when`(memberVerifyCodeReadUseCase.validate(any()))
            .thenReturn(verifyCodeRs)
        `when`(memberModifyMock.signup(memberSignUpDto, memberPrivateSignupDto))
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
        val memberSignUpDto = MemberSignUpDto(EMAIL, PW)
        val memberPrivateSignupDto = null
        val verifyCodeRs = MemberVerifyCodeResultVo(true, VERIFY_SUCCESS)
        val mockMemberSignUpResultVo = MemberSignUpResultVo(true, SUCCESS_MSG)

        `when`(memberMapper.toSignupDto(any())).thenReturn(memberSignUpDto)
        `when`(memberMapper.toSignupPrivateDto(any())).thenReturn(memberPrivateSignupDto)
        `when`(memberVerifyCodeReadUseCase.validate(any()))
            .thenReturn(verifyCodeRs)
        `when`(memberModifyMock.signup(memberSignUpDto, memberPrivateSignupDto))
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

    @Test
    fun `회원가입 요청 인증 코드 검증 - 실패`() {
        // given
        val reqBody = mapOf(
            "email" to EMAIL,
            "password" to PW,
            "confirmPassword" to PW,
            "emailVerifyCode" to VERIFY_CODE,
        )

        val memberSignUpDto = MemberSignUpDto(EMAIL, PW)
        val memberPrivateSignupDto = null
        val verifyCodeRs = MemberVerifyCodeResultVo(false, VERIFY_SUCCESS)

        `when`(memberMapper.toSignupDto(any())).thenReturn(memberSignUpDto)
        `when`(memberMapper.toSignupPrivateDto(any())).thenReturn(memberPrivateSignupDto)
        `when`(memberVerifyCodeReadUseCase.validate(any()))
            .thenReturn(verifyCodeRs)

        val resultAction = requestJsonPost(SIGNUP_URL, reqBody)

        // then
        resultAction.andExpect(MockMvcResultMatchers.status().is2xxSuccessful)
        val jsonNode = objectMapper.readTree(resultAction.andReturn().response.contentAsString)
        assertThat(jsonNode.get("data").get("verifyCodeResult").get("isSuccess").asBoolean()).isFalse
    }
}