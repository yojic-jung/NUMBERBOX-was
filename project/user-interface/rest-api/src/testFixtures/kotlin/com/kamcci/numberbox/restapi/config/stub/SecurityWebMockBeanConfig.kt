package com.kamcci.numberbox.restapi.config.stub

import com.kamcci.numberbox.restapi.config.WebConfig
import com.kamcci.numberbox.restapi.stub.common.MockUserDetailArgumentResolver
import jakarta.servlet.Filter
import jakarta.servlet.http.HttpServletRequest
import org.apache.catalina.security.SecurityConfig
import org.springframework.context.annotation.Bean
import org.springframework.security.web.SecurityFilterChain
import org.springframework.web.method.support.HandlerMethodArgumentResolver

class SecurityWebMockBeanConfig {

    @Bean
    fun securityConfig(): SecurityConfig = SecurityConfig.newInstance()

    @Bean
    fun securityFilterChain(): SecurityFilterChain = object : SecurityFilterChain {
        override fun matches(request: HttpServletRequest?): Boolean {
            return true
        }

        override fun getFilters(): MutableList<Filter> {
            return mutableListOf()
        }
    }

    @Bean
    fun handlerMethodArgumentResolver(): HandlerMethodArgumentResolver {
        return MockUserDetailArgumentResolver()
    }

    @Bean
    fun webConfig(): WebConfig {
        return WebConfig(handlerMethodArgumentResolver())
    }
}