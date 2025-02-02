package com.kamcci.numberbox.restapi.config.stub

import com.kamcci.modules.auth.control.service.TokenResponseService
import org.springframework.context.annotation.Bean
import java.util.*

class TokenMockBeanConfig {
    @Bean
    fun tokenResponseService(): TokenResponseService = object : TokenResponseService {
        override fun responseAuthToken(oldAccessToken: String?, oldRefreshToken: String?) {
        }

        override fun responseAuthToken(email: String?, userId: UUID?, roleList: MutableList<String>?) {
        }

        override fun setTokenToResponse(accessToken: String?, refreshToken: String?, roleList: MutableList<String>?) {

        }
    }
}