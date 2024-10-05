package com.kammci.numberbox.restapi.annotation

import com.kammci.numberbox.restapi.config.RestApiWebMvcMockBeanConfig
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration

@ActiveProfiles("rest-api")
@ContextConfiguration(classes = [RestApiWebMvcMockBeanConfig::class])
@WebMvcTest
annotation class WebMvcUnitTest
