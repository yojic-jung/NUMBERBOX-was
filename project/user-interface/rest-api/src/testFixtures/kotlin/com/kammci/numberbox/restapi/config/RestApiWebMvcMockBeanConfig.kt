package com.kammci.numberbox.restapi.config

import com.kamcci.numberbox.restapi.config.WebConfig
import com.kamcci.numberbox.restapi.config.member.MemberControllerConfig
import com.kamcci.numberbox.restapi.config.token.TokenConfig
import org.mockito.Mockito
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Import

@Import(
    MemberControllerConfig::class,
    TokenConfig::class
)
@TestConfiguration
class RestApiWebMvcMockBeanConfig {
    @Bean
    fun webConfig(): WebConfig = Mockito.mock()
}