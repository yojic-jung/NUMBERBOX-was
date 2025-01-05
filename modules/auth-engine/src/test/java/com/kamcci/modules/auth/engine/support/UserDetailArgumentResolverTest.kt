package com.kamcci.modules.auth.engine.support

import com.kamcci.modules.auth.control.annotation.UserEmail
import com.kamcci.modules.auth.control.annotation.UserId
import com.kamcci.modules.auth.control.annotation.UserRole
import com.kamcci.modules.auth.control.enumeration.UserRoleType
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.springframework.core.MethodParameter
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContext
import org.springframework.security.core.context.SecurityContextHolder
import java.util.*

class UserDetailArgumentResolverTest {

    private val parameter: MethodParameter = mock()
    private val securityContext: SecurityContext = mock()
    private val authentication: Authentication = mock()

    private val userDetailArgumentResolver = UserDetailArgumentResolver()

    @Test
    fun `supportsParameter 성공`() {
        // given
        `when`(parameter.getParameterAnnotation(UserId::class.java)).thenReturn(UserId())
        `when`(parameter.getParameterAnnotation(UserEmail::class.java)).thenReturn(UserEmail())
        `when`(parameter.getParameterAnnotation(UserRole::class.java)).thenReturn(UserRole())

        // when
        val isSupport = userDetailArgumentResolver.supportsParameter(parameter)

        // then
        assertThat(isSupport).isEqualTo(true)
    }

    @Test
    fun `supportsParameter(UserEmail 부착) -  성공`() {
        // given
        `when`(parameter.getParameterAnnotation(UserId::class.java)).thenReturn(null)
        `when`(parameter.getParameterAnnotation(UserEmail::class.java)).thenReturn(UserEmail())
        `when`(parameter.getParameterAnnotation(UserRole::class.java)).thenReturn(null)

        // when
        val isSupport = userDetailArgumentResolver.supportsParameter(parameter)

        // then
        assertThat(isSupport).isEqualTo(true)
    }

    @Test
    fun `supportsParameter(UserRole 부착) -  성공`() {
        // given
        `when`(parameter.getParameterAnnotation(UserId::class.java)).thenReturn(null)
        `when`(parameter.getParameterAnnotation(UserEmail::class.java)).thenReturn(null)
        `when`(parameter.getParameterAnnotation(UserRole::class.java)).thenReturn(UserRole())

        // when
        val isSupport = userDetailArgumentResolver.supportsParameter(parameter)

        // then
        assertThat(isSupport).isEqualTo(true)
    }

    @Test
    fun `supportsParameter(UserId 부착) -  성공`() {
        // given
        `when`(parameter.getParameterAnnotation(UserId::class.java)).thenReturn(UserId())
        `when`(parameter.getParameterAnnotation(UserEmail::class.java)).thenReturn(null)
        `when`(parameter.getParameterAnnotation(UserRole::class.java)).thenReturn(null)

        // when
        val isSupport = userDetailArgumentResolver.supportsParameter(parameter)

        // then
        assertThat(isSupport).isEqualTo(true)
    }

    @Test
    fun `supportsParameter 실패`() {
        // given
        `when`(parameter.getParameterAnnotation(UserId::class.java)).thenReturn(null)
        `when`(parameter.getParameterAnnotation(UserEmail::class.java)).thenReturn(null)
        `when`(parameter.getParameterAnnotation(UserRole::class.java)).thenReturn(null)

        // when
        val isSupport = userDetailArgumentResolver.supportsParameter(parameter)

        // then
        assertThat(isSupport).isEqualTo(false)
    }

    @Test
    fun `UserId 추출 - 성공`() {
        // given
        `when`(parameter.getParameterAnnotation(UserId::class.java)).thenReturn(UserId())
        val userId = UUID.randomUUID()
        `when`(authentication.isAuthenticated).thenReturn(true)
        `when`(authentication.details).thenReturn(userId)
        `when`(securityContext.authentication).thenReturn(authentication)
        SecurityContextHolder.setContext(securityContext)

        val principals = listOf("testUser", "anonymousUser")
        for ((idx, principal) in principals.withIndex()) {
            `when`(authentication.principal).thenReturn(principal)

            // when
            val resolveValue = userDetailArgumentResolver.resolveArgument(parameter, null, mock(), null)

            // then
            if (idx == 0)
                assertThat(resolveValue).isEqualTo(userId)
            else
                assertThat(resolveValue).isEqualTo(0)
        }
    }

    @Test
    fun `UserEmail 추출 - 성공`() {
        // given
        `when`(parameter.getParameterAnnotation(UserEmail::class.java)).thenReturn(UserEmail())
        `when`(authentication.isAuthenticated).thenReturn(true)
        `when`(securityContext.authentication).thenReturn(authentication)
        SecurityContextHolder.setContext(securityContext)

        val principals = listOf("testUser", "anonymousUser")
        for ((idx, principal) in principals.withIndex()) {
            `when`(authentication.principal).thenReturn(principal)

            // when
            val resolveValue = userDetailArgumentResolver.resolveArgument(parameter, null, mock(), null)

            // then
            if (idx == 0)
                assertThat(resolveValue).isEqualTo(principal)
            else
                assertThat(resolveValue).isEqualTo("")
        }
    }

    @Test
    fun `UserRole 추출 - 성공`() {
        // given
        `when`(authentication.isAuthenticated).thenReturn(true)
        `when`(authentication.authorities).thenReturn(
            listOf(
                SimpleGrantedAuthority(UserRoleType.USER.name),
                SimpleGrantedAuthority("nothing"),
            )
        )
        `when`(securityContext.authentication).thenReturn(authentication)
        SecurityContextHolder.setContext(securityContext)

        val principals = listOf("testUser", "anonymousUser")
        for ((idx, principal) in principals.withIndex()) {
            `when`(authentication.principal).thenReturn(principal)

            // when
            val resolveValue =
                userDetailArgumentResolver.resolveArgument(parameter, null, mock(), null) as List<UserRoleType>

            // then
            if (idx == 0)
                assertThat(resolveValue.contains(UserRoleType.USER)).isTrue()
            else
                assertThat(resolveValue.isEmpty()).isTrue()
        }
    }
}