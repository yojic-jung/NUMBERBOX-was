package com.kamcci.modules.logging.engine.service

import com.fasterxml.jackson.databind.ObjectMapper
import com.kamcci.modules.logging.control.dto.HttpRequestLoggingDto
import com.kamcci.modules.logging.control.service.RequestLoggingService
import com.kamcci.modules.logging.engine.config.LoggingTargetProperty
import com.kamcci.modules.logging.engine.util.BrowserOsUtil.browserLogging
import com.kamcci.modules.logging.engine.util.BrowserOsUtil.osLogging
import com.kamcci.modules.logging.engine.util.IPAddressUtil.getPublicIPAddress
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Service
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes
import org.springframework.web.util.ContentCachingRequestWrapper
import java.util.*

/**
 * request 정보 추출
 */
@Service
class HttpRequestLoggingService(
    private val loggingProperty: LoggingTargetProperty,
    private val objectMapper: ObjectMapper,
) : RequestLoggingService {

    companion object {
        // 쿼리 스트링 추출 http 메서드
        private val QUERY_STRING_METHOD = listOf(HttpMethod.GET, HttpMethod.DELETE).map { it.name() }

        // request body 추출 http 메서드
        private val REQUEST_BODY_METHOD = listOf(HttpMethod.PUT, HttpMethod.POST).map { it.name() }
    }

    // Request 정보 로깅
    override fun logging(): HttpRequestLoggingDto? {
        val attr = RequestContextHolder.getRequestAttributes()
        attr as ServletRequestAttributes
        val request: HttpServletRequest = attr.request

        // 로깅 대상 아닌 경우 제외
        if (!isLoggingUri(request)) return null

        // userId
        val userId = request.getAttribute("userId") ?: return null

        // uri, http method, req_body, os, ip, browser
        val reqUri = request.requestURI
        val method = request.method
        val os = osLogging(request.getHeader("sec-ch-ua-platform"))
        val browser = browserLogging(request.getHeader("user-agent"), os)
        val clientIp = getPublicIPAddress(request)

        // request Body 로깅 대상만 로깅
        val reqBody = if (isBodyLogging(request)) {
            // requestBody 또는 쿼리스트링 추출
            getRequestBodyOrQueryString(request)
        } else {
            // 로깅제외 대상은 null
            null
        }

        return HttpRequestLoggingDto(
            memberId = userId as UUID,
            browser = browser.attrName,
            os = os.attrName,
            ip = clientIp,
            method = method,
            uri = reqUri,
            reqBody = reqBody,
        )
    }

    // 로깅 대상 uri인지 판별
    private fun isLoggingUri(request: HttpServletRequest): Boolean {
        // 사용자 제외 설정 uri는 로깅 제외
        val exceptUriList = loggingProperty.exceptUri
        return exceptUriList == null || !exceptUriList.contains(request.requestURI)
    }

    // reqBody 로깅 대상인지 판별
    private fun isBodyLogging(request: HttpServletRequest): Boolean {
        // 사용자 제외 설정 uri는 로깅 제외
        val reqUri = request.requestURI
        return if (loggingProperty.bodyExceptUri != null
            && loggingProperty.bodyExceptUri.any { it == reqUri }
        ) {
            false
        } else {
            // 사용자 설정 contentType만 body 로깅
            val contentType = request.getHeader("Content-Type")
            contentType != null && loggingProperty.contentType.any { contentType.contains(it) }
        }
    }


    // http 메서드 구분하여 요청 값 추출
    private fun getRequestBodyOrQueryString(
        request: HttpServletRequest,
    ): String? {
        return when {
            QUERY_STRING_METHOD.contains(request.method.uppercase()) -> {
                if (request.parameterMap.isNotEmpty()) objectMapper.writeValueAsString(request.parameterMap) else null
            }

            REQUEST_BODY_METHOD.contains(request.method.uppercase()) -> {
                val cachedRequest = request as ContentCachingRequestWrapper
                cachedRequest.contentAsString
            }

            else -> null
        }
    }
}