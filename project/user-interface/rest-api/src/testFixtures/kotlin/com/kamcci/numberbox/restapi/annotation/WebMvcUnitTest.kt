package com.kamcci.numberbox.restapi.annotation

import com.kamcci.numberbox.restapi.config.RestApiWebMvcMockBeanConfig
import com.kamcci.numberbox.restapi.controller.cs.CsErrorReportReadController
import com.kamcci.numberbox.restapi.controller.cs.CsErrorReportWriteController
import com.kamcci.numberbox.restapi.controller.members.*
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.context.ContextConfiguration

@ContextConfiguration(classes = [RestApiWebMvcMockBeanConfig::class])
@WebMvcTest(
    value = [
        LoginFailureController::class,
        MemberWriteController::class,
        MemberPrivateWriteController::class,
        MemberProfileWriteController::class,
        MemberFindController::class,
        MembersFollowWriteController::class,
        CsErrorReportReadController::class,
        CsErrorReportWriteController::class,
    ]
)
annotation class WebMvcUnitTest
