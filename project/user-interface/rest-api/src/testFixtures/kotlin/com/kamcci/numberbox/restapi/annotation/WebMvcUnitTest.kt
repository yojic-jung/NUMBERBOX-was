package com.kamcci.numberbox.restapi.annotation

import com.kamcci.numberbox.restapi.config.RestApiWebMvcMockBeanConfig
import com.kamcci.numberbox.restapi.controller.HealthCheck
import com.kamcci.numberbox.restapi.controller.cs.CsErrorReportReadController
import com.kamcci.numberbox.restapi.controller.cs.CsErrorReportWriteController
import com.kamcci.numberbox.restapi.controller.docs.MathDocsReadController
import com.kamcci.numberbox.restapi.controller.docs.MathDocsUsageWriteController
import com.kamcci.numberbox.restapi.controller.docs.MathDocsWriteController
import com.kamcci.numberbox.restapi.controller.hwp.HwpConvertController
import com.kamcci.numberbox.restapi.controller.hwp.HwpConvertEventController
import com.kamcci.numberbox.restapi.controller.hwp.MyHwpController
import com.kamcci.numberbox.restapi.controller.math.*
import com.kamcci.numberbox.restapi.controller.members.*
import com.kamcci.numberbox.restapi.controller.resource.MathResourceMenuReadController
import com.kamcci.numberbox.restapi.controller.resource.MathResourceReadController
import com.kamcci.numberbox.restapi.controller.resource.MathResourceWriteController
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration

/**
 * 컨트롤러 슬라이스 테스트 목적 컨텍스트 환경
 */
@ContextConfiguration(classes = [RestApiWebMvcMockBeanConfig::class])
@WebMvcTest(
    value = [
        HealthCheck::class,
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
        MathResourceMenuReadController::class,
        MathResourceReadController::class,
        MathResourceWriteController::class,
        HwpConvertController::class,
        MyHwpController::class,
        HwpConvertEventController::class
    ]
)
@ActiveProfiles("rest-api")
annotation class WebMvcUnitTest
