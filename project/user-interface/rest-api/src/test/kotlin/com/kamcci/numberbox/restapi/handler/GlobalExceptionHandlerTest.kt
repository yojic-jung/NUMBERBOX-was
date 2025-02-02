package com.kamcci.numberbox.restapi.handler

import com.kamcci.numberbox.app.domain.exception.BusinessInValidException
import com.kamcci.numberbox.restapi.util.response.ResponseErrMsg
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.BeanInstantiationException
import org.springframework.mock.web.MockHttpServletRequest
import org.springframework.web.context.request.ServletWebRequest

class GlobalExceptionHandlerTest {
    companion object {
        const val EX_MSG = "mocking exception message"
        const val PATH = "uri"
    }

    private val exceptionHandler = GlobalExceptionHandler()
    lateinit var webRequest: ServletWebRequest

    @BeforeEach
    fun `테스트 데이터 초기화`() {
        val mockServletWebRequest = MockHttpServletRequest()
        webRequest = ServletWebRequest(mockServletWebRequest)
        mockServletWebRequest.contextPath = PATH
    }

    @Test
    fun `IllegalArgumentException 핸들러 - 성공`() {
        // given
        val exception = IllegalArgumentException(EX_MSG)

        // when
        val responseEntity = exceptionHandler.handleIllegalArgumentException(
            ex = exception,
            request = webRequest
        )

        // then
        val errMsg = responseEntity.body as ResponseErrMsg
        assertThat(errMsg.message).isEqualTo(EX_MSG)
        assertThat(errMsg.path).isEqualTo(PATH)
    }

    @Test
    fun `BeanInstantiationException 핸들러 - 성공`() {
        // given
        val exception = BeanInstantiationException(Any::class.java, EX_MSG)

        // when
        val responseEntity = exceptionHandler.handleIllegalArgumentException(
            ex = exception,
            request = webRequest
        )

        // then
        val errMsg = responseEntity.body as ResponseErrMsg
        assertThat(errMsg.path).isEqualTo(PATH)
    }

    @Test
    fun `BusinessValidException 핸들러 - 성공`() {
        // given
        val exception = BusinessInValidException(EX_MSG)

        // when
        val responseEntity = exceptionHandler.handleBusinessInValidException(
            ex = exception,
            body = null,
            request = webRequest
        )

        // then
        val errMsg = responseEntity.body as ResponseErrMsg
        assertThat(errMsg.message).isEqualTo(EX_MSG)
        assertThat(errMsg.path).isEqualTo(PATH)
    }

    @Test
    fun `Exception 핸들러 - 성공`() {
        // given
        val exception = Exception(EX_MSG)

        // when
        val responseEntity = exceptionHandler.handleException(
            ex = exception,
            body = null,
            request = webRequest
        )

        // then
        val errMsg = responseEntity.body as ResponseErrMsg
        assertThat(errMsg.message).isEqualTo(EX_MSG)
        assertThat(errMsg.path).isEqualTo(PATH)
    }
}