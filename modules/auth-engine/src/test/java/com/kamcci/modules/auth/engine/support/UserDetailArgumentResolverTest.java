package com.kamcci.modules.auth.engine.support;

import com.kamcci.modules.auth.control.annotation.UserEmail;
import com.kamcci.modules.auth.control.annotation.UserId;
import com.kamcci.modules.auth.control.annotation.UserRole;
import com.kamcci.modules.auth.control.enumeration.UserRoleType;
import com.kamcci.modules.auth.engine.dto.JwtAuthenticationToken;
import org.assertj.core.api.InstanceOfAssertFactories;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.core.MethodParameter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

class UserDetailArgumentResolverTest {
    private final MethodParameter parameter = Mockito.mock(MethodParameter.class);
    private final SecurityContext securityContext = Mockito.mock(SecurityContext.class);
    private final Authentication authentication = Mockito.mock(Authentication.class);
    private final UserDetailArgumentResolver userDetailArgumentResolver = new UserDetailArgumentResolver();

    @Test
    void supportsParameter_성공() {
        // given
        Mockito.when(parameter.getParameterAnnotation(UserId.class)).thenReturn(Mockito.mock(UserId.class));
        Mockito.when(parameter.getParameterAnnotation(UserEmail.class)).thenReturn(Mockito.mock(UserEmail.class));
        Mockito.when(parameter.getParameterAnnotation(UserRole.class)).thenReturn(Mockito.mock(UserRole.class));

        // when
        boolean isSupport = userDetailArgumentResolver.supportsParameter(parameter);

        // then
        assertThat(isSupport).isTrue();
    }

    @Test
    void supportsParameter_UserEmail_부착_성공() {
        // given
        Mockito.when(parameter.getParameterAnnotation(UserId.class)).thenReturn(null);
        Mockito.when(parameter.getParameterAnnotation(UserEmail.class)).thenReturn(Mockito.mock(UserEmail.class));
        Mockito.when(parameter.getParameterAnnotation(UserRole.class)).thenReturn(null);

        // when
        boolean isSupport = userDetailArgumentResolver.supportsParameter(parameter);

        // then
        assertThat(isSupport).isTrue();
    }

    @Test
    void supportsParameter_UserRole_부착_성공() {
        // given
        Mockito.when(parameter.getParameterAnnotation(UserId.class)).thenReturn(null);
        Mockito.when(parameter.getParameterAnnotation(UserEmail.class)).thenReturn(null);
        Mockito.when(parameter.getParameterAnnotation(UserRole.class)).thenReturn(Mockito.mock(UserRole.class));

        // when
        boolean isSupport = userDetailArgumentResolver.supportsParameter(parameter);

        // then
        assertThat(isSupport).isTrue();
    }

    @Test
    void supportsParameter_UserId_부착_성공() {
        // given
        Mockito.when(parameter.getParameterAnnotation(UserId.class)).thenReturn(Mockito.mock(UserId.class));
        Mockito.when(parameter.getParameterAnnotation(UserEmail.class)).thenReturn(null);
        Mockito.when(parameter.getParameterAnnotation(UserRole.class)).thenReturn(null);

        // when
        boolean isSupport = userDetailArgumentResolver.supportsParameter(parameter);

        // then
        assertThat(isSupport).isTrue();
    }

    @Test
    void supportsParameter_실패() {
        // given
        Mockito.when(parameter.getParameterAnnotation(UserId.class)).thenReturn(null);
        Mockito.when(parameter.getParameterAnnotation(UserEmail.class)).thenReturn(null);
        Mockito.when(parameter.getParameterAnnotation(UserRole.class)).thenReturn(null);

        // when
        boolean isSupport = userDetailArgumentResolver.supportsParameter(parameter);

        // then
        assertThat(isSupport).isFalse();
    }

    @Test
    void UserId_추출_성공() {
        // given
        Mockito.when(parameter.getParameterAnnotation(UserId.class)).thenReturn(Mockito.mock(UserId.class));
        Map<String, Object> details = new HashMap<>();
        details.put(UserId.ATTR_NAME, UUID.randomUUID());
        Mockito.when(authentication.isAuthenticated()).thenReturn(true);
        Mockito.when(authentication.getDetails()).thenReturn(details);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        List<String> principals = Arrays.asList("testUser", "anonymousUser");
        for(int idx = 0; idx < principals.size(); idx++) {
            Mockito.when(authentication.getPrincipal()).thenReturn(principals.get(idx));

            // when
            Object resolveValue = userDetailArgumentResolver.resolveArgument(parameter, null, Mockito.mock(), null);

            // then
            if(idx == 0) {
                assertThat(resolveValue).isEqualTo(details.get(UserId.ATTR_NAME));
            } else {
                assertThat(resolveValue).isEqualTo(0);
            }
        }
    }

    @Test
    void UserEmail_추출_성공() {
        // given
        Mockito.when(parameter.getParameterAnnotation(UserEmail.class)).thenReturn(Mockito.mock(UserEmail.class));
        Mockito.when(authentication.isAuthenticated()).thenReturn(true);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        List<String> principals = Arrays.asList("testUser", "anonymousUser");
        for(int idx = 0; idx < principals.size(); idx++) {
            Mockito.when(authentication.getPrincipal()).thenReturn(principals.get(idx));

            // when
            Object resolveValue = userDetailArgumentResolver.resolveArgument(parameter, null, Mockito.mock(), null);

            // then
            if(idx == 0) {
                assertThat(resolveValue).isEqualTo(principals.get(idx));
            } else {
                assertThat(resolveValue).isEqualTo("");
            }
        }
    }

    @Test
    void 익명_사용자_권한_없음_성공() {
        // given
        List<SimpleGrantedAuthority> roles = List.of(new SimpleGrantedAuthority(UserRoleType.USER.name()));
        SecurityContextImpl context = new SecurityContextImpl();
        Authentication auth = new JwtAuthenticationToken("anonymousUser", "", roles);
        context.setAuthentication(auth);
        SecurityContextHolder.setContext(context);

        // when
        List<UserRoleType> roleList = (List<UserRoleType>) userDetailArgumentResolver.resolveArgument(parameter, null
                , Mockito.mock(), null);

        // then
        assertThat(roleList).isEmpty();

    }

    @Test
    void UserRole_추출_성공() {
        // given
        List<SimpleGrantedAuthority> roles = List.of(new SimpleGrantedAuthority(UserRoleType.USER.name()));
        SecurityContextImpl context = new SecurityContextImpl();
        Authentication auth = new JwtAuthenticationToken("", "", roles);
        context.setAuthentication(auth);
        SecurityContextHolder.setContext(context);

        List<String> principals = Arrays.asList("testUser", "anonymousUser");
        for(int idx = 0; idx < principals.size(); idx++) {
            Mockito.when(authentication.getPrincipal()).thenReturn(principals.get(idx));

            // when
            Object resolveValue = userDetailArgumentResolver.resolveArgument(parameter, null, Mockito.mock(), null);

            // then
            assertThat(resolveValue).isInstanceOf(List.class)
                    .asInstanceOf(InstanceOfAssertFactories.list(Object.class)) // Generic 검증
                    .contains(UserRoleType.USER);

        }
    }
}
