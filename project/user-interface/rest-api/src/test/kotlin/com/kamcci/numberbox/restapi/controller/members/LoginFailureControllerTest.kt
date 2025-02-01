package com.kamcci.numberbox.restapi.controller.members

import com.kamcci.modules.auth.control.exception.BadAuthRequestException
import com.kamcci.modules.auth.control.exception.DisabledUserException
import com.kamcci.modules.auth.control.exception.PasswordMissMatchException
import com.kamcci.modules.auth.control.exception.UserNotFoundException
import com.kamcci.numberbox.app.service.constant.FailConstant.FAIL_EMAIL
import com.kamcci.numberbox.app.usecase.member.MemberLoginFailureUseCase
import com.kamcci.numberbox.restapi.annotation.WebMvcUnitTest
import com.kamcci.numberbox.restapi.common.BaseMockMvcTest
import com.kamcci.numberbox.restapi.util.response.ResponseErrMsg
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.mock.web.MockHttpServletRequest

@WebMvcUnitTest
class LoginFailureControllerTest(
    @Autowired
    private val loginFailureController: LoginFailureController,
    @Autowired
    private val memberLoginFailureUseCase: MemberLoginFailureUseCase
) : BaseMockMvcTest() {
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
        mockRequest.setAttribute("username", "success@test.com")

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
        mockRequest.setAttribute("username", FAIL_EMAIL)

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
        mockRequest.setAttribute("username", "success@test.com")

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
        mockRequest.setAttribute("username", FAIL_EMAIL)

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
        mockRequest.setAttribute("username", "success@test.com")

        // when
        val result = loginFailureController.loginFailCallback(mockRequest)

        // then
        val errMsg = result.body as ResponseErrMsg
        assertThat(errMsg.status).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR.value())
    }
}