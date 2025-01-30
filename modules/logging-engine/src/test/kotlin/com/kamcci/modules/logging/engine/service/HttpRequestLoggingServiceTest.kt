package com.kamcci.modules.logging.engine.service

import com.fasterxml.jackson.databind.ObjectMapper
import com.kammci.modules.logging.engine.dummy.config.LoggingDummyData.getLoggingTargetProperty
import com.kammci.modules.logging.engine.stub.util.MockIPAddressUtil
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.mock.web.MockHttpServletRequest
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes
import org.springframework.web.util.ContentCachingRequestWrapper
import java.util.*

class HttpRequestLoggingServiceTest {
    companion object {
        const val URI = "/example-uri"
        const val CONTENT_TYPE = "application/json"
    }

    lateinit var mockRequest: MockHttpServletRequest

    @BeforeEach
    fun `mockRequest 설정`() {
        mockRequest = MockHttpServletRequest()
        mockRequest.requestURI = URI
        mockRequest.method = "POST"
        mockRequest.setAttribute("userId", UUID.randomUUID())
        mockRequest.addHeader("Content-Type", CONTENT_TYPE)
        mockRequest.addHeader("sec-ch-ua-platform", "Windows")
        mockRequest.addHeader("user-agent", "Mozilla/5.0 (Windows NT 10.0)")
        mockRequest.addHeader("X-Forwarded-For", "203.0.113.1, 198.51.100.1")

        RequestContextHolder.setRequestAttributes(ServletRequestAttributes(ContentCachingRequestWrapper(mockRequest)))
    }

    @Test
    fun `Request 로깅 제외 대상 판별 - 성공(로깅 제외 uri 미설정)`() {
        // given
        val loggingService =
            HttpRequestLoggingService(
                MockIPAddressUtil(),
                getLoggingTargetProperty(),
                ObjectMapper()
            )

        // when
        val loggingDto = loggingService.logging()

        // then
        assertThat(loggingDto).isNotNull()
    }


    @Test
    fun `Request 로깅 제외 대상 판별 - 성공(로깅 제외 uri 아닌 경우)`() {
        // given
        val exceptUri = listOf(URI.reversed()) // 로깅 제외 uri 설정
        val loggingService =
            HttpRequestLoggingService(
                MockIPAddressUtil(),
                getLoggingTargetProperty(exceptUri = exceptUri),
                ObjectMapper()
            )

        // when
        val loggingDto = loggingService.logging()

        // then
        assertThat(loggingDto).isNotNull()
    }


    @Test
    fun `Request 로깅 제외 대상 판별 - 성공(로깅 대상 uri)`() {
        // given
        val exceptUri = listOf(URI) // 로깅 제외 uri 설정
        val loggingService =
            HttpRequestLoggingService(
                MockIPAddressUtil(),
                getLoggingTargetProperty(exceptUri = exceptUri),
                ObjectMapper()
            )

        // when
        val loggingDto = loggingService.logging()

        // then
        assertThat(loggingDto).isNull()
    }

    @Test
    fun `Request 정보 로깅 - 실패(userId 미존재)`() {
        // given
        val loggingService =
            HttpRequestLoggingService(
                MockIPAddressUtil(),
                getLoggingTargetProperty(),
                ObjectMapper()
            )
        // userId 미존재
        mockRequest.setAttribute("userId", null)
        RequestContextHolder.setRequestAttributes(ServletRequestAttributes(ContentCachingRequestWrapper(mockRequest)))

        // when
        val loggingDto = loggingService.logging()

        // then
        assertThat(loggingDto).isNull()
    }


    @Test
    fun `Content-Type = null로 reqBody 로깅 제외 - 성공()`() {
        // given
        val bodyExceptUri = listOf("")
        val loggingService =
            HttpRequestLoggingService(
                MockIPAddressUtil(),
                getLoggingTargetProperty(bodyExceptUri = bodyExceptUri),
                ObjectMapper()
            )
        mockRequest.removeHeader("Content-Type")
        RequestContextHolder.setRequestAttributes(ServletRequestAttributes(ContentCachingRequestWrapper(mockRequest)))

        // when
        val loggingDto = loggingService.logging()

        // then
        assertThat(loggingDto!!.reqBody).isNull()
    }

    @Test
    fun `Content-Type은 empty로 reqBody 로깅 제외 - 성공()`() {
        // given
        val bodyExceptUri = listOf("")
        val loggingService =
            HttpRequestLoggingService(
                MockIPAddressUtil(),
                getLoggingTargetProperty(bodyExceptUri = bodyExceptUri),
                ObjectMapper()
            )
        mockRequest.removeHeader("Content-Type")
        mockRequest.addHeader("Content-Type", "")
        RequestContextHolder.setRequestAttributes(ServletRequestAttributes(ContentCachingRequestWrapper(mockRequest)))

        // when
        val loggingDto = loggingService.logging()

        // then
        assertThat(loggingDto!!.reqBody).isNull()
    }

    @Test
    fun `reqBody 로깅 제외 uri인 경우 reqBody 제외하고 Request 정보 로깅 - 성공()`() {
        // given
        val bodyExceptUri = listOf(URI)
        val loggingService =
            HttpRequestLoggingService(
                MockIPAddressUtil(),
                getLoggingTargetProperty(bodyExceptUri = bodyExceptUri),
                ObjectMapper()
            )

        // when
        val loggingDto = loggingService.logging()

        // then
        assertThat(loggingDto!!.reqBody).isNull()
    }

    @Test
    fun `reqBody 로깅 제외 uri 아니지만 사용자 설정 contentType 아니어서 reqBody 제외하고 로깅 - 성공()`() {
        // given
        val bodyExceptUri = listOf("random")
        val contentType = listOf("multipart/form")
        val loggingService =
            HttpRequestLoggingService(
                MockIPAddressUtil(),
                getLoggingTargetProperty(contentType = contentType, bodyExceptUri = bodyExceptUri),
                ObjectMapper()
            )

        // when
        val loggingDto = loggingService.logging()

        // then
        assertThat(loggingDto!!.reqBody).isNull()
    }


    @Test
    fun `GET 요청 파라미터맵 없는 경우 로깅 - 성공()`() {
        // given
        mockRequest.method = "GET"
        RequestContextHolder.setRequestAttributes(ServletRequestAttributes(ContentCachingRequestWrapper(mockRequest)))
        val loggingService =
            HttpRequestLoggingService(
                MockIPAddressUtil(),
                getLoggingTargetProperty(),
                ObjectMapper()
            )

        // when
        val loggingDto = loggingService.logging()

        // then
        assertThat(loggingDto!!.reqBody).isNull()
    }

    @Test
    fun `GET 요청 파라미터맵 존재 로깅 - 성공()`() {
        // given
        mockRequest.method = "GET"
        val key = "key"
        val value = "value"
        mockRequest.setParameter(key, value)
        RequestContextHolder.setRequestAttributes(ServletRequestAttributes(ContentCachingRequestWrapper(mockRequest)))
        val loggingService =
            HttpRequestLoggingService(
                MockIPAddressUtil(),
                getLoggingTargetProperty(),
                ObjectMapper()
            )

        // when
        val loggingDto = loggingService.logging()

        // then
        assertThat(loggingDto!!.reqBody).contains(key)
        assertThat(loggingDto.reqBody).contains(value)
    }


    @Test
    fun `POST 요청 reqBody 로깅 - 성공()`() {
        // given
        mockRequest.method = "POST"
        mockRequest.characterEncoding = "UTF-8"
        mockRequest.setContent("""{"key":"value"}""".toByteArray())
        val wrappedRequest = ContentCachingRequestWrapper(mockRequest)
        wrappedRequest.inputStream.readBytes()
        RequestContextHolder.setRequestAttributes(ServletRequestAttributes(wrappedRequest))
        val loggingService =
            HttpRequestLoggingService(
                MockIPAddressUtil(),
                getLoggingTargetProperty(),
                ObjectMapper()
            )

        // when
        val loggingDto = loggingService.logging()

        // then
        assertThat(loggingDto!!.reqBody).contains(mockRequest.contentAsString)
    }

    @Test
    fun `HEAD 요청은 파라미터 맵도 reqBody도 로깅 안함 - 성공()`() {
        // given
        mockRequest.method = "HEAD"
        RequestContextHolder.setRequestAttributes(ServletRequestAttributes(ContentCachingRequestWrapper(mockRequest)))
        val loggingService =
            HttpRequestLoggingService(
                MockIPAddressUtil(),
                getLoggingTargetProperty(),
                ObjectMapper()
            )

        // when
        val loggingDto = loggingService.logging()

        // then
        assertThat(loggingDto!!.reqBody).isNull()
    }

}