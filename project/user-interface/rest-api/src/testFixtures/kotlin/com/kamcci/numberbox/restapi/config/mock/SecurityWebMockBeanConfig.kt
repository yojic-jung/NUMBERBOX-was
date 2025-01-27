package com.kamcci.numberbox.restapi.config.mock

import com.kamcci.numberbox.restapi.config.WebConfig
import com.kamcci.numberbox.restapi.resolver.MockUserDetailArgumentResolver
import org.apache.catalina.security.SecurityConfig
import org.mockito.Mockito
import org.springframework.context.annotation.Bean
import org.springframework.security.web.SecurityFilterChain
import org.springframework.web.method.support.HandlerMethodArgumentResolver

class SecurityWebMockBeanConfig {

    @Bean
    fun securityConfig(): SecurityConfig = Mockito.mock()

    @Bean
    fun securityFilterChain(): SecurityFilterChain = Mockito.mock()

    @Bean
    fun handlerMethodArgumentResolver(): HandlerMethodArgumentResolver {
        return MockUserDetailArgumentResolver()
    }

    @Bean
    fun webConfig(): WebConfig {
        return WebConfig(handlerMethodArgumentResolver())
    }
}