package com.kamcci.numberbox.restapi.config

import com.kamcci.numberbox.restapi.config.mock.*
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Import

@Import(
    SecurityWebMockBeanConfig::class,
    CsMockBeanConfig::class,
    FileMockBeanConfig::class,
    MathCategoryMockBeanConfig::class,
    MathContentsMockBeanConfig::class,
    MathDocsMockBeanConfig::class,
    MathResourceMockBeanConfig::class,
    MemberMockBeanConfig::class,
    TokenMockBeanConfig::class,
)
@TestConfiguration
class RestApiWebMvcMockBeanConfig