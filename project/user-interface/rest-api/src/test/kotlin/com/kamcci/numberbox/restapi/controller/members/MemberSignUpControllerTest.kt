package com.kamcci.numberbox.restapi.controller.members

import com.kamcci.numberbox.app.domain.dto.member.MemberSignUpDto
import com.kamcci.numberbox.app.domain.enumeration.member.VerifyCodeType
import com.kamcci.numberbox.app.domain.exception.BusinessInValidException
import com.kamcci.numberbox.app.domain.vo.member.MemberSignUpResultVo
import com.kamcci.numberbox.app.usecase.member.MemberReadCase
import com.kamcci.numberbox.app.usecase.member.MemberWriteCase
import com.kamcci.numberbox.restapi.annotation.WebMvcUnitTest
import com.kamcci.numberbox.restapi.common.BaseMockMvcTest
import com.kamcci.numberbox.restapi.mapper.member.MemberMapper
import org.junit.jupiter.api.Test
import org.mockito.Mockito.`when`
import org.mockito.kotlin.any
import org.mockito.kotlin.anyOrNull
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.MethodArgumentNotValidException
import java.util.*

@WebMvcUnitTest
class MemberSignUpControllerTest @Autowired constructor(
    private val memberReadCase: MemberReadCase,
    private val memberWriteCase: MemberWriteCase,
    private val memberMapper: MemberMapper,
) : BaseMockMvcTest() {
    companion object {
        const val PREFIX = "/public/member"
        const val SIGNUP_URL = "$PREFIX/signup"
        const val CREATE_VERIFY_CODE_URL = "$PREFIX/signup/verifyCode"

        // 테스트 데이터
        const val EMAIL = "test@test.com"
        const val PW = "abcd1234!"
        const val VERIFY_CODE = "3e0c5f0e-3e12-488c-be48-88fdb92c2dd0"
        const val NAME = "홍길동"
        const val PHONE = "01012341234"
        const val BIRTH = "650123"
    }

    @Test
    fun `회원가입 목적 인증 코드 생성 요청 - 성공`() {
        // given
        val reqBody = mapOf(
            "email" to EMAIL,
            "codeType" to VerifyCodeType.SignUp
        )
        `when`(memberReadCase.existEmail(any())).thenReturn(false)

        //when
        val resultAction = postRequest(CREATE_VERIFY_CODE_URL, reqBody)

        // then
        assert2xx(resultAction)
    }

    @Test
    fun `회원가입 목적 인증 코드 생성 요청 - 실패(중복 이메일 존재)`() {
        // given
        val reqBody = mapOf(
            "email" to EMAIL,
            "codeType" to VerifyCodeType.SignUp
        )
        `when`(memberReadCase.existEmail(any())).thenReturn(true)

        //when
        val resultAction = postRequest(CREATE_VERIFY_CODE_URL, reqBody)

        // then
        assert4xx(resultAction)
        assertException(resultAction, BusinessInValidException::class)
    }


    @Test
    fun `유효하지 않은 이메일로 인증 코드 생성 요청- 실패`() {
        // given
        val reqBody = mapOf(
            "email" to "testtest.com",
            "codeType" to VerifyCodeType.SignUp
        )

        //when
        val resultAction = postRequest(CREATE_VERIFY_CODE_URL, reqBody)

        // then
        assert4xx(resultAction)
        assertException(resultAction, MethodArgumentNotValidException::class)
    }

    @Test
    fun `회원가입 - 성공`() {
        // given
        val reqBody = mapOf(
            "email" to EMAIL,
            "password" to PW,
            "confirmPassword" to PW,
            "emailVerifyCode" to VERIFY_CODE,
        )
        val memberSignupDto = MemberSignUpDto("", "")
        `when`(memberMapper.toSignupDto(any())).thenReturn(memberSignupDto)
        `when`(memberWriteCase.signup(any(), anyOrNull())).thenReturn(
            MemberSignUpResultVo(UUID.randomUUID(), "", listOf(""))
        )

        //when
        val resultAction = postRequest(SIGNUP_URL, reqBody)

        // then
        assert2xx(resultAction)
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
        val resultAction = postRequest(SIGNUP_URL, reqBody)

        // then
        assert4xx(resultAction)
        assertException(resultAction, MethodArgumentNotValidException::class)
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
        val resultAction = postRequest(SIGNUP_URL, reqBody)

        // then
        assert4xx(resultAction)
        assertException(resultAction, HttpMessageNotReadableException::class)
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
        val resultAction = postRequest(SIGNUP_URL, reqBody)

        // then
        assert4xx(resultAction)
        assertException(resultAction, HttpMessageNotReadableException::class)
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
        val resultAction = postRequest(SIGNUP_URL, reqBody)

        // then
        assert4xx(resultAction)
        assertException(resultAction, MethodArgumentNotValidException::class)
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
        val resultAction = postRequest(SIGNUP_URL, reqBody)

        // then
        assert4xx(resultAction)
        assertException(resultAction, MethodArgumentNotValidException::class)
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
        val resultAction = postRequest(SIGNUP_URL, reqBody)

        // then
        assert4xx(resultAction)
        assertException(resultAction, MethodArgumentNotValidException::class)
    }


}