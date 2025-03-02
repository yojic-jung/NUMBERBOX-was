package com.kamcci.modules.logging.engine.service

import com.fasterxml.jackson.databind.ObjectMapper
import com.kammci.modules.logging.engine.mock.util.MockIPAddressUtil
import com.kammci.modules.logging.engine.sample.config.LoggingSampleData.getLoggingTargetProperty
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
        const val REQ_URI = "/example-uri"
        const val CONTENT_TYPE = "application/json"
    }

    lateinit var mockRequest: MockHttpServletRequest
    lateinit var loggingService: HttpRequestLoggingService


    // 테스트 대상 객체 설정
    private fun setUpTarget(
        contentType: List<String> = listOf(""),
        exceptUri: List<String>? = null,
        bodyExceptUri: List<String>? = null
    ) {
        loggingService =
            HttpRequestLoggingService(
                MockIPAddressUtil(),
                getLoggingTargetProperty(
                    contentType = contentType,
                    exceptUri = exceptUri,
                    bodyExceptUri = bodyExceptUri
                ),
                ObjectMapper()
            )
    }


    @BeforeEach
    fun `mockRequest 설정`() {
        mockRequest = MockHttpServletRequest()
        mockRequest.requestURI = REQ_URI
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
        setUpTarget(exceptUri = null)

        // when
        val loggingDto = loggingService.logging()

        // then
        assertThat(loggingDto).isNotNull()
    }


    @Test
    fun `Request 로깅 제외 대상 판별 - 성공(로깅 제외 uri 아닌 경우)`() {
        // given - 요청 uri가 아닌 uri로 로깅 제외 uri 설정
        val exceptUri = listOf(REQ_URI.reversed())
        setUpTarget(exceptUri = exceptUri)

        // when
        val loggingDto = loggingService.logging()

        // then
        assertThat(loggingDto).isNotNull()
    }


    @Test
    fun `Request 로깅 제외 대상 판별 - 성공(로깅 대상 uri)`() {
        // given
        setUpTarget(exceptUri = listOf(REQ_URI)) // 로깅 제외 uri 설정

        // when
        val loggingDto = loggingService.logging()

        // then
        assertThat(loggingDto).isNull()
    }

    @Test
    fun `Request 정보 로깅 - 실패(userId 미존재)`() {
        // given
        setUpTarget()
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
        setUpTarget(bodyExceptUri = bodyExceptUri)

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
        setUpTarget(bodyExceptUri = bodyExceptUri)

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
        val bodyExceptUri = listOf(REQ_URI)
        setUpTarget(bodyExceptUri = bodyExceptUri)

        // when
        val loggingDto = loggingService.logging()

        // then
        assertThat(loggingDto!!.reqBody).isNull()
    }

    @Test
    fun `reqBody 로깅 제외 uri 아니지만 사용자 설정 contentType 아니어서 reqBody 제외하고 로깅 - 성공()`() {
        // given
        val bodyExceptUri = listOf("any")
        val contentType = listOf("multipart/form")
        setUpTarget(contentType = contentType, bodyExceptUri = bodyExceptUri)

        // when
        val loggingDto = loggingService.logging()

        // then
        assertThat(loggingDto!!.reqBody).isNull()
    }


    @Test
    fun `GET 요청 파라미터맵 없는 경우 로깅 - 성공()`() {
        // given
        setUpTarget()
        mockRequest.method = "GET"
        RequestContextHolder.setRequestAttributes(ServletRequestAttributes(ContentCachingRequestWrapper(mockRequest)))

        // when
        val loggingDto = loggingService.logging()

        // then
        assertThat(loggingDto!!.reqBody).isNull()
    }

    @Test
    fun `GET 요청 파라미터맵 존재 로깅 - 성공()`() {
        // given
        setUpTarget()
        mockRequest.method = "GET"
        val key = "anyKey"
        val value = "anyValue"
        mockRequest.setParameter(key, value)
        RequestContextHolder.setRequestAttributes(ServletRequestAttributes(ContentCachingRequestWrapper(mockRequest)))

        // when
        val loggingDto = loggingService.logging()

        // then
        assertThat(loggingDto!!.reqBody).contains(key)
        assertThat(loggingDto.reqBody).contains(value)
    }


    @Test
    fun `POST 요청 reqBody 로깅 - 성공()`() {
        // given
        setUpTarget()
        mockRequest.method = "POST"
        mockRequest.characterEncoding = "UTF-8"
        mockRequest.setContent("""{"key":"value"}""".toByteArray())
        val wrappedRequest = ContentCachingRequestWrapper(mockRequest)
        wrappedRequest.inputStream.readBytes()
        RequestContextHolder.setRequestAttributes(ServletRequestAttributes(wrappedRequest))

        // when
        val loggingDto = loggingService.logging()

        // then
        assertThat(loggingDto!!.reqBody).contains(mockRequest.contentAsString)
    }

    @Test
    fun `HEAD 요청은 파라미터 맵도 reqBody도 로깅 안함 - 성공()`() {
        // given
        setUpTarget()
        mockRequest.method = "HEAD"
        RequestContextHolder.setRequestAttributes(ServletRequestAttributes(ContentCachingRequestWrapper(mockRequest)))

        // when
        val loggingDto = loggingService.logging()

        // then
        assertThat(loggingDto!!.reqBody).isNull()
    }

}