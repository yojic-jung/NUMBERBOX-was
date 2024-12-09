package com.kamcci.numberbox.restapi.config.token

import com.kamcci.modules.auth.control.service.TokenResponseService
import org.mockito.Mockito
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean

@TestConfiguration
class TokenConfig {
    @Bean
    fun tokenResponseService(): TokenResponseService = Mockito.mock()
}