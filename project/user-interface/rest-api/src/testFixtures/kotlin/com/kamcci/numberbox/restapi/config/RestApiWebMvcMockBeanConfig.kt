package com.kamcci.numberbox.restapi.config

import com.kamcci.numberbox.restapi.config.member.CsControllerConfig
import com.kamcci.numberbox.restapi.config.token.TokenConfig
import com.kamcci.numberbox.restapi.resolver.MockUserDetailArgumentResolver
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Import
import org.springframework.web.method.support.HandlerMethodArgumentResolver

@Import(
    CsControllerConfig::class,
    TokenConfig::class
)
@TestConfiguration
class RestApiWebMvcMockBeanConfig {
    @Bean
    fun handlerMethodArgumentResolver(): HandlerMethodArgumentResolver {
        return MockUserDetailArgumentResolver()
    }

    @Bean
    fun webConfig(): WebConfig {
        return WebConfig(handlerMethodArgumentResolver())
    }
}