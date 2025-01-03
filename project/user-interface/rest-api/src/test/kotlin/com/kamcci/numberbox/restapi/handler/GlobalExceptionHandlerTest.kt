package com.kamcci.numberbox.restapi.handler

import com.kamcci.numberbox.app.domain.exception.BusinessInValidException
import com.kamcci.numberbox.restapi.util.response.ResponseErrMsg
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.springframework.beans.BeanInstantiationException
import org.springframework.web.context.request.WebRequest

class GlobalExceptionHandlerTest {
    private val exceptionHandler = GlobalExceptionHandler()

    @Test
    fun `IllegalArgumentException 핸들러 - 성공`() {
        // given
        val mockingMsg = "mocking exception message"
        val path = "uri"
        val exception = mock(IllegalArgumentException::class.java)
        `when`(exception.message).thenReturn(mockingMsg)
        // Mock WebRequest 생성
        val webRequest = mock(WebRequest::class.java)
        `when`(webRequest.contextPath).thenReturn(path)

        // when
        val responseEntity = exceptionHandler.handleIllegalArgumentException(
            ex = exception,
            request = webRequest
        )

        // then
        val errMsg = responseEntity.body as ResponseErrMsg
        assertThat(errMsg.message).isEqualTo(mockingMsg)
        assertThat(errMsg.path).isEqualTo(path)
    }

    @Test
    fun `BeanInstantiationException 핸들러 - 성공`() {
        // given
        val mockingMsg = "mocking exception message"
        val path = "uri"
        val exception = mock(BeanInstantiationException::class.java)
        `when`(exception.message).thenReturn(mockingMsg)
        // Mock WebRequest 생성
        val webRequest = mock(WebRequest::class.java)
        `when`(webRequest.contextPath).thenReturn(path)

        // when
        val responseEntity = exceptionHandler.handleIllegalArgumentException(
            ex = exception,
            request = webRequest
        )

        // then
        val errMsg = responseEntity.body as ResponseErrMsg
        assertThat(errMsg.message).isEqualTo(mockingMsg)
        assertThat(errMsg.path).isEqualTo(path)
    }

    @Test
    fun `BusinessValidException 핸들러 - 성공`() {
        // given
        val mockingMsg = "mocking exception message"
        val path = "uri"
        val exception = mock(BusinessInValidException::class.java)
        `when`(exception.message).thenReturn(mockingMsg)
        // Mock WebRequest 생성
        val webRequest = mock(WebRequest::class.java)
        `when`(webRequest.contextPath).thenReturn(path)

        // when
        val responseEntity = exceptionHandler.handleBusinessInValidException(
            ex = exception,
            body = null,
            request = webRequest
        )

        // then
        val errMsg = responseEntity.body as ResponseErrMsg
        assertThat(errMsg.message).isEqualTo(mockingMsg)
        assertThat(errMsg.path).isEqualTo(path)
    }

    @Test
    fun `Exception 핸들러 - 성공`() {
        // given
        val mockingMsg = "mocking exception message"
        val path = "uri"
        val exception = mock(Exception::class.java)
        `when`(exception.message).thenReturn(mockingMsg)
        // Mock WebRequest 생성
        val webRequest = mock(WebRequest::class.java)
        `when`(webRequest.contextPath).thenReturn(path)

        // when
        val responseEntity = exceptionHandler.handleException(
            ex = exception,
            body = null,
            request = webRequest
        )

        // then
        val errMsg = responseEntity.body as ResponseErrMsg
        assertThat(errMsg.message).isEqualTo(mockingMsg)
        assertThat(errMsg.path).isEqualTo(path)
    }
}