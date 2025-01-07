//package com.kamcci.modules.auth.engine.support
//
//import com.kamcci.modules.auth.control.annotation.UserEmail
//import com.kamcci.modules.auth.control.annotation.UserId
//import com.kamcci.modules.auth.control.annotation.UserRole
//import com.kamcci.modules.auth.control.enumeration.UserRoleType
//import org.springframework.beans.factory.annotation.Qualifier
//import org.springframework.core.MethodParameter
//import org.springframework.security.core.context.SecurityContextHolder
//import org.springframework.stereotype.Component
//import org.springframework.web.bind.support.WebDataBinderFactory
//import org.springframework.web.context.request.NativeWebRequest
//import org.springframework.web.method.support.HandlerMethodArgumentResolver
//import org.springframework.web.method.support.ModelAndViewContainer
//import java.util.*
//
//@Component
//@Qualifier("userDetail")
//class UserDetailArgumentResolver : HandlerMethodArgumentResolver {
//    override fun supportsParameter(parameter: MethodParameter) =
//        parameter.getParameterAnnotation(UserId::class.java) != null ||
//                parameter.getParameterAnnotation(UserEmail::class.java) != null ||
//                parameter.getParameterAnnotation(UserRole::class.java) != null
//
//    override fun resolveArgument(
//        parameter: MethodParameter,
//        mavContainer: ModelAndViewContainer?,
//        webRequest: NativeWebRequest,
//        binderFactory: WebDataBinderFactory?
//    ): Any {
//        val authentication = SecurityContextHolder.getContext().authentication
//
//        val hasUserIDAnnot = parameter.getParameterAnnotation(UserId::class.java) != null
//        val hasUserEmailAnnot = parameter.getParameterAnnotation(UserEmail::class.java) != null
//        val isAnonymousUser = authentication.principal == "anonymousUser"
//
//        return when {
//            // @UserID 적용 및 인증된 사용자
//            hasUserIDAnnot -> {
//                if (isAnonymousUser) 0
//                else authentication.details as UUID
//            }
//
//            // @UserEmail 적용 및 인증된 사용자
//            hasUserEmailAnnot -> {
//                if (isAnonymousUser) ""
//                else authentication.principal.toString()
//            }
//
//            // @UserRole 적용 및 인증된 사용자
//            else -> {
//                if (isAnonymousUser) listOf<UserRoleType>()
//                else {
//                    val roles = authentication.authorities.map { it.authority }
//                    val roleTypeList: MutableList<UserRoleType?> = mutableListOf()
//                    roles.forEach { role ->
//                        roleTypeList.add(UserRoleType.entries.find { it.name == role })
//                    }
//                    roleTypeList
//                }
//            }
//        }
//    }
//}