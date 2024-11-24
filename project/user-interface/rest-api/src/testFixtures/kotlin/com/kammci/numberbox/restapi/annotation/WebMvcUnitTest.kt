package com.kammci.numberbox.restapi.annotation

import com.kamcci.numberbox.restapi.controller.members.*
import com.kammci.numberbox.restapi.config.RestApiWebMvcMockBeanConfig
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration

@ActiveProfiles("rest-api")
@ContextConfiguration(classes = [RestApiWebMvcMockBeanConfig::class])
@WebMvcTest(
    value = [
        LoginFailureController::class,
        MemberWriteController::class,
        MemberPrivateWriteController::class,
        MemberProfileWriteController::class,
        MemberFindController::class,
        MembersFollowWriteController::class,
    ]
)
annotation class WebMvcUnitTest
