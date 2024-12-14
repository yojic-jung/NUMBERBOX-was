package com.kamcci.numberbox.restapi.config

import com.kamcci.numberbox.restapi.config.mock.CsMockBeanConfig
import com.kamcci.numberbox.restapi.config.mock.FileMockBeanConfig
import com.kamcci.numberbox.restapi.config.mock.MemberMockBeanConfig
import com.kamcci.numberbox.restapi.config.mock.TokenMockBeanConfig
import com.kamcci.numberbox.restapi.resolver.MockUserDetailArgumentResolver
import org.apache.catalina.security.SecurityConfig
import org.mockito.Mockito
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Import
import org.springframework.security.web.SecurityFilterChain
import org.springframework.web.method.support.HandlerMethodArgumentResolver

@Import(
    CsMockBeanConfig::class,
    MemberMockBeanConfig::class,
    FileMockBeanConfig::class,
    TokenMockBeanConfig::class
)
@TestConfiguration
class RestApiWebMvcMockBeanConfig {

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