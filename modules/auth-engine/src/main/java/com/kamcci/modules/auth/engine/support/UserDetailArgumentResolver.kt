package com.kamcci.modules.auth.engine.support

import com.kamcci.modules.auth.engine.annotation.UserEmail
import com.kamcci.modules.auth.engine.annotation.UserID
import com.kamcci.modules.auth.engine.annotation.UserRole
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.core.MethodParameter
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.method.support.ModelAndViewContainer
import java.util.*

@Component
@Qualifier("userDetail")
class UserDetailArgumentResolver : HandlerMethodArgumentResolver {
    override fun supportsParameter(parameter: MethodParameter) =
        parameter.getParameterAnnotation(UserID::class.java) != null ||
                parameter.getParameterAnnotation(UserEmail::class.java) != null ||
                parameter.getParameterAnnotation(UserRole::class.java) != null

    override fun resolveArgument(
        parameter: MethodParameter,
        mavContainer: ModelAndViewContainer?,
        webRequest: NativeWebRequest,
        binderFactory: WebDataBinderFactory?
    ): Any {
        val authentication = SecurityContextHolder.getContext().authentication

        val hasUserIDAnnot = parameter.getParameterAnnotation(UserID::class.java) != null
        val hasUserEmailAnnot = parameter.getParameterAnnotation(UserEmail::class.java) != null
        val isAnonymousUser = authentication.principal == "anonymousUser"


        return when {
            // @UserID 적용 및 인증된 사용자
            hasUserIDAnnot && !isAnonymousUser -> authentication.details as UUID
            // @UserID 적용 및 익명 사용자
            hasUserIDAnnot && isAnonymousUser -> 0

            // @UserEmail 적용 및 인증된 사용자
            hasUserEmailAnnot && !isAnonymousUser -> authentication.principal.toString()
            // @UserEmail 적용 및 익명 사용자
            hasUserEmailAnnot && isAnonymousUser -> ""

            // @UserRole 적용 및 인증된 사용자
            isAnonymousUser -> authentication.authorities.map { it.authority }
            // @UserRole 적용 및 익명 사용자
            else -> listOf<String>()
        }
    }
}