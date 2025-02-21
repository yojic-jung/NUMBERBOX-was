package com.kamcci.numberbox.restapi.mock.common

import com.kamcci.modules.auth.control.annotation.UserEmail
import com.kamcci.modules.auth.control.annotation.UserId
import com.kamcci.modules.auth.control.annotation.UserRole
import com.kamcci.numberbox.app.service.constant.MockTestConstant.FAIL_MEMBER_ID
import org.springframework.core.MethodParameter
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.method.support.ModelAndViewContainer
import java.util.*

class MockUserDetailArgumentResolver : HandlerMethodArgumentResolver {
    companion object {
        val ID_FROM_RESOLVER = UUID.randomUUID()
        val FAIL_REQ_SETTING = "fail"
        val EMAIL_FROM_RESOLVER = "test@test.com"
        val ROLE_FROM_RESOLVER = listOf("USER")
    }

    override fun supportsParameter(parameter: MethodParameter) =
        parameter.getParameterAnnotation(UserId::class.java) != null ||
                parameter.getParameterAnnotation(UserEmail::class.java) != null ||
                parameter.getParameterAnnotation(UserRole::class.java) != null

    override fun resolveArgument(
        parameter: MethodParameter,
        mavContainer: ModelAndViewContainer?,
        webRequest: NativeWebRequest,
        binderFactory: WebDataBinderFactory?
    ): Any {
        val hasUserIDAnnot = parameter.getParameterAnnotation(UserId::class.java) != null
        val hasUserEmailAnnot = parameter.getParameterAnnotation(UserEmail::class.java) != null


        return when {
            // @UserID 적용
            hasUserIDAnnot -> {
                if (webRequest.getAttribute(FAIL_REQ_SETTING, 0) != null) FAIL_MEMBER_ID
                else ID_FROM_RESOLVER
            }

            // @UserEmail 적용
            hasUserEmailAnnot -> EMAIL_FROM_RESOLVER

            // @UserRole 적용
            else -> ROLE_FROM_RESOLVER
        }
    }
}