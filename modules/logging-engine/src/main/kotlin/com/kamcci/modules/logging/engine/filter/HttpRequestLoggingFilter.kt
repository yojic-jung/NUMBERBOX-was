package com.kamcci.modules.logging.engine.filter

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import org.springframework.web.util.ContentCachingRequestWrapper

/**
 * request 로깅을 위해 ContentCachingRequestWrapper로 래핑하여 넘김
 *
 * - ContentCachingRequestWrapper는 쿼리스트링과 reqBody를 호출할 때 캐싱을 진행함
 */
@Order(-1)
@Component
class HttpRequestLoggingFilter : OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val cachingRequest = ContentCachingRequestWrapper(request)
        filterChain.doFilter(cachingRequest, response)
    }
}