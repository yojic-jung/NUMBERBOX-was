package com.kamcci.numberbox.restapi.config

import com.kamcci.modules.auth.control.service.TokenResponseService
import com.kamcci.modules.mail.sender.service.MailSendService
import com.kamcci.numberbox.app.port.email.member.MemberVerifyCodeEmailPort
import com.kamcci.numberbox.app.port.etc.MemberPasswordEncoder
import com.kamcci.numberbox.app.service.constant.MockTestConstant.FAIL_STRING
import com.kamcci.numberbox.app.service.mock.port.email.member.MockMemberVerifyCodeEmailPort
import com.kamcci.numberbox.restapi.mock.common.MockUserDetailArgumentResolver
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import java.util.*

@TestConfiguration
class IntegrationMockBeanConfiguration {
    @Bean
    fun memberVerifyCodeEmailPort(): MemberVerifyCodeEmailPort = MockMemberVerifyCodeEmailPort()

    @Bean
    fun tokenResponseService(): TokenResponseService = object : TokenResponseService {
        override fun responseAuthToken(oldAccessToken: String?, oldRefreshToken: String?) {
        }

        override fun responseAuthToken(email: String?, userId: UUID?, roleList: MutableList<String>?) {
        }

        override fun setTokenToResponse(accessToken: String?, refreshToken: String?, roleList: MutableList<String>?) {
        }
    }

    @Bean
    fun authPasswordEncoderWrapper(): MemberPasswordEncoder = object : MemberPasswordEncoder {
        override fun encode(rawPassword: CharSequence): String {
            return rawPassword.toString()
        }

        override fun matches(rawPassword: CharSequence, encodedPassword: String): Boolean {
            return encodedPassword != FAIL_STRING
        }
    }

    @Bean
    fun mailSendService(): MailSendService = object : MailSendService {
        override fun sendHTMLMessage(recipientEmail: String, title: String, contents: String) {

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