package com.kamcci.numberbox.restapi.config

import com.kamcci.modules.auth.control.service.TokenResponseService
import com.kamcci.modules.mail.sender.service.MailSendService
import com.kamcci.numberbox.app.port.email.member.MemberVerifyCodeEmailPort
import com.kamcci.numberbox.restapi.resolver.MockUserDetailArgumentResolver
import com.kamcci.numberbox.restapi.util.auth.AuthPasswordEncoderWrapper
import org.mockito.Mockito
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.web.method.support.HandlerMethodArgumentResolver

@TestConfiguration
class IntegrationMockBeanConfiguration {
    @Bean
    fun memberVerifyCodeEmailPort(): MemberVerifyCodeEmailPort = Mockito.mock()

    @Bean
    fun tokenResponseService(): TokenResponseService = Mockito.mock()

    @Bean
    fun authPasswordEncoderWrapper(): AuthPasswordEncoderWrapper = Mockito.mock()

    @Bean
    fun mailSendService(): MailSendService = Mockito.mock()

    @Bean
    fun handlerMethodArgumentResolver(): HandlerMethodArgumentResolver {
        return MockUserDetailArgumentResolver()
    }

    @Bean
    fun webConfig(): WebConfig {
        return WebConfig(handlerMethodArgumentResolver())
    }
}