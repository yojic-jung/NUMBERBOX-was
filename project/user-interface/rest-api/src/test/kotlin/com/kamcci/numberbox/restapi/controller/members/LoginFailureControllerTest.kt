package com.kamcci.numberbox.restapi.controller.members

import com.kamcci.modules.auth.control.exception.BadAuthRequestException
import com.kamcci.modules.auth.control.exception.DisabledUserException
import com.kamcci.modules.auth.control.exception.PasswordMissMatchException
import com.kamcci.modules.auth.control.exception.UserNotFoundException
import com.kamcci.numberbox.app.usecase.member.MemberLoginFailureUseCase
import com.kamcci.numberbox.restapi.util.response.ResponseErrMsg
import com.kammci.numberbox.restapi.annotation.WebMvcUnitTest
import com.kammci.numberbox.restapi.common.BaseMockMvcTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.kotlin.any
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.mock.web.MockHttpServletRequest

@WebMvcUnitTest
class LoginFailureControllerTest : BaseMockMvcTest() {
    @Autowired
    lateinit var loginFailureController: LoginFailureController

    @Autowired
    lateinit var memberLoginFailureUsecase: MemberLoginFailureUseCase

    @Test
    fun `로그인 실패 콜백 - 요청 형식 오류`() {
        // given
        val mockRequest = MockHttpServletRequest()
        mockRequest.setAttribute("auth.error.exception", BadAuthRequestException())
        mockRequest.setAttribute("username", "test@test.com")

        // when
        val result = loginFailureController.loginFailCallback(mockRequest)

        // then
        val errMsg = result.body as ResponseErrMsg
        assertThat(errMsg.status).isEqualTo(HttpStatus.BAD_REQUEST.value())
    }

    @Test
    fun `로그인 실패 콜백 - 계정 존재하지 않음`() {
        // given
        val mockRequest = MockHttpServletRequest()
        mockRequest.setAttribute("auth.error.exception", UserNotFoundException())
        mockRequest.setAttribute("username", "test@test.com")

        // when
        val result = loginFailureController.loginFailCallback(mockRequest)

        // then
        val errMsg = result.body as ResponseErrMsg
        assertThat(errMsg.status).isEqualTo(HttpStatus.UNAUTHORIZED.value())
    }

    @Test
    fun `로그인 실패 콜백 - 비밀번호 불일치 잠금`() {
        // given
        val mockRequest = MockHttpServletRequest()
        mockRequest.setAttribute("auth.error.exception", PasswordMissMatchException())
        mockRequest.setAttribute("username", "test@test.com")
        Mockito.`when`(memberLoginFailureUsecase.disableUserIfFailCountOver(any())).thenReturn(true)

        // when
        val result = loginFailureController.loginFailCallback(mockRequest)

        // then
        val errMsg = result.body as ResponseErrMsg
        assertThat(errMsg.status).isEqualTo(HttpStatus.FORBIDDEN.value())
    }


    @Test
    fun `로그인 실패 콜백 - 비밀번호 불일치`() {
        // given
        val mockRequest = MockHttpServletRequest()
        mockRequest.setAttribute("auth.error.exception", PasswordMissMatchException())
        mockRequest.setAttribute("username", "test@test.com")
        Mockito.`when`(memberLoginFailureUsecase.disableUserIfFailCountOver(any())).thenReturn(false)

        // when
        val result = loginFailureController.loginFailCallback(mockRequest)

        // then
        val errMsg = result.body as ResponseErrMsg
        assertThat(errMsg.status).isEqualTo(HttpStatus.FORBIDDEN.value())
    }

    @Test
    fun `로그인 실패 콜백 - 비활성화된 계정`() {
        // given
        val mockRequest = MockHttpServletRequest()
        mockRequest.setAttribute("auth.error.exception", DisabledUserException())
        mockRequest.setAttribute("username", "test@test.com")
        Mockito.`when`(memberLoginFailureUsecase.ableUserIfDisableTimeOver(any())).thenReturn(true)

        // when
        val result = loginFailureController.loginFailCallback(mockRequest)

        // then
        val errMsg = result.body as ResponseErrMsg
        assertThat(errMsg.status).isEqualTo(HttpStatus.FORBIDDEN.value())
    }

    @Test
    fun `로그인 실패 콜백 - 비활성화된 계정 잠금`() {
        // given
        val mockRequest = MockHttpServletRequest()
        mockRequest.setAttribute("auth.error.exception", DisabledUserException())
        mockRequest.setAttribute("username", "test@test.com")
        Mockito.`when`(memberLoginFailureUsecase.ableUserIfDisableTimeOver(any())).thenReturn(false)

        // when
        val result = loginFailureController.loginFailCallback(mockRequest)

        // then
        val errMsg = result.body as ResponseErrMsg
        assertThat(errMsg.status).isEqualTo(HttpStatus.FORBIDDEN.value())
    }

    @Test
    fun `로그인 실패 콜백 - 서버 예외`() {
        // given
        val mockRequest = MockHttpServletRequest()
        mockRequest.setAttribute("auth.error.exception", RuntimeException())
        mockRequest.setAttribute("username", "test@test.com")
        Mockito.`when`(memberLoginFailureUsecase.ableUserIfDisableTimeOver(any())).thenReturn(false)

        // when
        val result = loginFailureController.loginFailCallback(mockRequest)

        // then
        val errMsg = result.body as ResponseErrMsg
        assertThat(errMsg.status).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR.value())
    }
}