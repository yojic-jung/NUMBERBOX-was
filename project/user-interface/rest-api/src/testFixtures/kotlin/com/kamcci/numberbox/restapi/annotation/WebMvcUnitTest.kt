package com.kamcci.numberbox.restapi.annotation

import com.kamcci.numberbox.restapi.config.RestApiWebMvcMockBeanConfig
import com.kamcci.numberbox.restapi.controller.cs.CsErrorReportReadController
import com.kamcci.numberbox.restapi.controller.cs.CsErrorReportWriteController
import com.kamcci.numberbox.restapi.controller.docs.MathDocsReadController
import com.kamcci.numberbox.restapi.controller.docs.MathDocsUsageWriteController
import com.kamcci.numberbox.restapi.controller.docs.MathDocsWriteController
import com.kamcci.numberbox.restapi.controller.math.*
import com.kamcci.numberbox.restapi.controller.members.*
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration

@ContextConfiguration(classes = [RestApiWebMvcMockBeanConfig::class])
@WebMvcTest(
    value = [
        LoginFailureController::class,
        MemberReadController::class,
        MemberWriteController::class,
        MemberPrivateWriteController::class,
        MemberProfileWriteController::class,
        MemberProfileReadController::class,
        MemberFindController::class,
        MembersFollowWriteController::class,
        MemberSignUpController::class,
        CsErrorReportReadController::class,
        CsErrorReportWriteController::class,
        MathDocsReadController::class,
        MathDocsWriteController::class,
        MathDocsUsageWriteController::class,
        MathMenuReadController::class,
        MathContentsWriteController::class,
        MathContentsReadController::class,
        ManagerContentsWriteController::class,
        MathContentsLikeWriteController::class,
        MathContentsRepoWriteController::class,
        MathContentsLikeReadController::class,
    ]
)
@ActiveProfiles("rest-api")
annotation class WebMvcUnitTest
