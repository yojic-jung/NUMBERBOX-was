package com.kamcci.numberbox.restapi.controller.members

import com.kamcci.numberbox.app.domain.enumeration.member.VerifyCodeType
import com.kamcci.numberbox.app.usecase.member.MemberReadUseCase
import com.kammci.numberbox.restapi.annotation.WebMvcUnitTest
import com.kammci.numberbox.restapi.common.BaseMockMvcTest
import org.junit.jupiter.api.Test
import org.mockito.Mockito.`when`
import org.mockito.kotlin.any
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@WebMvcUnitTest
class MemberPublicControllerTest : BaseMockMvcTest() {
    companion object {
        private const val CREATE_VERIFY_CODE_URL = "/public/member/signup/verifyCode"
        private const val SIGNUP_URL = "/public/member/signUp"

        private const val FIND_EMAIL_URL = "/public/member/findEmail"
        private const val FIND_PASSWD_URL = "/public/member/findPassword"

        // 정상 케이스 테스트 케이스
        private const val EMAIL = "test@test.com"
        private const val PW = "abcd1234!"
        private const val VERIFY_CODE = "3e0c5f0e-3e12-488c-be48-88fdb92c2dd0"
        private const val NAME = "홍길동"
        private const val PHONE = "01012341234"
        private const val BIRTH = "650123"
    }

    @Autowired
    lateinit var memberReadUseCase: MemberReadUseCase

    @Test
    fun `회원가입 목적 인증 코드 생성 요청 - 성공`() {
        // given
        val reqBody = mapOf(
            "email" to EMAIL,
            "codeType" to VerifyCodeType.SignUp
        )
        `when`(memberReadUseCase.existEmail(any())).thenReturn(false)

        //when
        val resultAction = requestJsonPost(CREATE_VERIFY_CODE_URL, reqBody)

        // then
        resultAction.andExpect(MockMvcResultMatchers.status().is2xxSuccessful)
    }

    @Test
    fun `회원가입 목적 인증 코드 생성 요청 - 실패(중복 이메일 존재)`() {
        // given
        val reqBody = mapOf(
            "email" to EMAIL,
            "codeType" to VerifyCodeType.SignUp
        )
        `when`(memberReadUseCase.existEmail(any())).thenReturn(false)

        //when
        val resultAction = requestJsonPost(CREATE_VERIFY_CODE_URL, reqBody)

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
    fun `유효하지 않은 이메일로 인증 코드 생성 요청- 실패`() {
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
        val resultAction = requestGet(FIND_EMAIL_URL, reqBody)

        // then
        resultAction.andExpect(MockMvcResultMatchers.status().is2xxSuccessful)
    }

    @Test
    fun `이메일 찾기 - 실패(유효하지 않은 이름)`() {
        // given
        val reqBody = mapOf(
            "userName" to "이",
            "phoneNumber" to "01012341234"
        )

        //when
        val resultAction = requestGet(FIND_EMAIL_URL, reqBody)

        // then
        resultAction.andExpect(MockMvcResultMatchers.status().is4xxClientError)
    }

    @Test
    fun `이메일 찾기 - 실패(유효하지 않은 휴대폰 번호)`() {
        // given
        val reqBody = mapOf(
            "userName" to "이름",
            "phoneNumber" to "010123456789"
        )

        //when
        val resultAction = requestGet(FIND_EMAIL_URL, reqBody)

        // then
        resultAction.andExpect(MockMvcResultMatchers.status().is4xxClientError)
    }

    @Test
    fun `비밀번호 찾기 - 실패(유효하지 않은 이메일)`() {
        // given
        val reqBody = mapOf("email" to "email")

        //when
        val resultAction = requestGet(FIND_PASSWD_URL, reqBody)

        // then
        resultAction.andExpect(MockMvcResultMatchers.status().is4xxClientError)
    }

    @Test
    fun `비밀번호 찾기 - 성공`() {
        // given
        val reqBody = mapOf("email" to "test@test.com")

        //when
        val resultAction = requestGet(FIND_PASSWD_URL, reqBody)

        // then
        resultAction.andExpect(MockMvcResultMatchers.status().is2xxSuccessful)
    }
}