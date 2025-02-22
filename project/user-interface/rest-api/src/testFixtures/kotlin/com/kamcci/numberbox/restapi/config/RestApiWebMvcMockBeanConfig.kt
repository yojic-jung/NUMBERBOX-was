package com.kamcci.numberbox.restapi.config

import com.kamcci.numberbox.restapi.config.mock.*
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Import

@Import(
    SecurityWebMockBeanConfig::class,
    CsMockBeanConfig::class,
    FileMockBeanConfig::class,
    MockMathContentsBeanConfig::class,
    MathDocsMockBeanConfig::class,
    MathResourceMockBeanConfig::class,
    MemberMockBeanConfig::class,
    TokenMockBeanConfig::class,
    HwpMockBeanConfig::class,
)
@TestConfiguration
class RestApiWebMvcMockBeanConfig