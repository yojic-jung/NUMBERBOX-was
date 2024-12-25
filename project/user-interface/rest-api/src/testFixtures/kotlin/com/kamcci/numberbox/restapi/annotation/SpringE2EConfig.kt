package com.kamcci.numberbox.restapi.annotation

import com.kamcci.numberbox.restapi.config.IntegrationMockBeanConfiguration
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration

@ContextConfiguration(classes = [IntegrationMockBeanConfiguration::class])
@ActiveProfiles("rest-api", "orm-jpa-adapter-tc-test", "storage", "storage-env")
annotation class SpringE2EConfig
