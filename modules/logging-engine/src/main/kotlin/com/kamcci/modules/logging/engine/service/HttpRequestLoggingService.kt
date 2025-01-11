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
 * Def : request 정보 추출
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
        val request: HttpServletRequest =
            (RequestContextHolder.getRequestAttributes() as ServletRequestAttributes).request

        // 로깅 대상 아닌 경우 제외
        if (!isLoggingTarget(request)) return null


        // userId todo auth에서 끌고 들어와야함
        val userId = request.getAttribute("userId") as UUID
        // uri, http method, req_body, os, ip, browser
        val reqUri = request.requestURI
        val method = request.method
        val os = osLogging(request.getHeader("sec-ch-ua-platform"))
        val browser = browserLogging(request.getHeader("user-agent"), os.attrName)
        val clientIp = getPublicIPAddress(request)


        // request Body 로깅 제외 uri는 null 선언
        val reqBody = if (loggingProperty.bodyExceptUri.any { it.contains(reqUri) || reqUri.contains(it) }) {
            // 로깅제외 대상은 null
            null
        } else {
            // requestBody 또는 쿼리스트링 추출
            getRequestBodyOrQueryString(request)
        }

        return HttpRequestLoggingDto(
            memberId = userId,
            browser = browser.attrName,
            os = os.attrName,
            ip = clientIp,
            method = method,
            uri = reqUri,
            reqBody = reqBody,
        )
    }

    // 로깅 대상인지 판별
    private fun isLoggingTarget(request: HttpServletRequest): Boolean {
        // 사용자 제외 설정 uri는 로깅 제외
        val exceptUriList = loggingProperty.exceptUri
        if (exceptUriList.contains(request.requestURI)) return false

        // contentType이 application/json인 경우만 로깅
        val contentType = request.getHeader("Content-Type") as String
        return loggingProperty.contentType.any { contentType.contains(it) }
    }


    // http 메서드 구분하여 요청 값 추출
    private fun getRequestBodyOrQueryString(
        request: HttpServletRequest,
    ): String? {
        return when {
            QUERY_STRING_METHOD.contains(request.method) -> {
                if (request.parameterMap.isNotEmpty()) objectMapper.writeValueAsString(request.parameterMap) else null
            }

            REQUEST_BODY_METHOD.contains(request.method) -> {
                val cachedRequest = request as ContentCachingRequestWrapper
                cachedRequest.contentAsString
            }

            else -> null
        }
    }
}