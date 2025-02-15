package com.kamcci.modules.auth.engine.support;

import com.kamcci.modules.auth.control.annotation.UserId;
import com.kamcci.modules.auth.control.enumeration.UserRoleType;
import com.kamcci.modules.auth.dummy.UserParameterInfo;
import com.kamcci.modules.auth.engine.dto.JwtAuthenticationToken;
import com.kamcci.modules.auth.stub.common.MockNativeWebRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.MethodParameter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

class UserDetailArgumentResolverTest {
    // 테스트 대상
    private final UserDetailArgumentResolver userDetailArgumentResolver = new UserDetailArgumentResolver();
    // 테스트 데이터
    private final Map<String, Object> details = new HashMap<>();
    private SecurityContext securityContext;

    @BeforeEach
    void 테스트_초기화() {
        details.put(UserId.ATTR_NAME, UUID.randomUUID());
        securityContext = new SecurityContextImpl();
        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    void supportsParameter_UserId_부착_성공() throws NoSuchMethodException {
        // given
        MethodParameter parameter = new MethodParameter(UserParameterInfo.class.getDeclaredMethod("supportAllAnnot",
                UUID.class, String.class, List.class), 0);

        // when
        boolean isSupport = userDetailArgumentResolver.supportsParameter(parameter);

        // then
        assertThat(isSupport).isTrue();
    }

    @Test
    void supportsParameter_UserEmail_부착_성공() throws NoSuchMethodException {
        // given
        MethodParameter parameter = new MethodParameter(UserParameterInfo.class.getDeclaredMethod("supportAllAnnot",
                UUID.class, String.class, List.class), 1);

        // when
        boolean isSupport = userDetailArgumentResolver.supportsParameter(parameter);

        // then
        assertThat(isSupport).isTrue();
    }

    @Test
    void supportsParameter_UserRole_부착_성공() throws NoSuchMethodException {
        // given
        MethodParameter parameter = new MethodParameter(UserParameterInfo.class.getDeclaredMethod("supportAllAnnot",
                UUID.class, String.class, List.class), 2);

        // when
        boolean isSupport = userDetailArgumentResolver.supportsParameter(parameter);

        // then
        assertThat(isSupport).isTrue();
    }

    @Test
    void supportsParameter_실패() throws NoSuchMethodException {
        // given
        MethodParameter parameter = new MethodParameter(UserParameterInfo.class.getDeclaredMethod("notSupportAnnot",
                UUID.class), 0);

        // when
        boolean isSupport = userDetailArgumentResolver.supportsParameter(parameter);

        // then
        assertThat(isSupport).isFalse();
    }

    @Test
    void UserId_추출_성공() throws NoSuchMethodException {
        // given
        MethodParameter parameter = new MethodParameter(UserParameterInfo.class.getDeclaredMethod("supportAllAnnot",
                UUID.class, String.class, List.class), 0);
        JwtAuthenticationToken authentication = new JwtAuthenticationToken("principal", "credentials",
                new ArrayList<>());
        authentication.setDetails(details);

        securityContext.setAuthentication(authentication);

        // when
        Object resolveValue = userDetailArgumentResolver.resolveArgument(parameter, null, new MockNativeWebRequest(),
                null);

        // then
        assertThat(resolveValue).isEqualTo(details.get(UserId.ATTR_NAME));
    }

    @Test
    void UserId_추출_실패_익명사용자() throws NoSuchMethodException {
        // given
        MethodParameter parameter = new MethodParameter(UserParameterInfo.class.getDeclaredMethod("supportAllAnnot",
                UUID.class, String.class, List.class), 0);
        JwtAuthenticationToken authentication = new JwtAuthenticationToken("anonymousUser", "credentials",
                new ArrayList<>());
        authentication.setDetails(details);
        securityContext.setAuthentication(authentication);

        // when
        Object resolveValue = userDetailArgumentResolver.resolveArgument(parameter, null, new MockNativeWebRequest(),
                null);

        // then
        assertThat(resolveValue).isNull();
    }

    @Test
    void UserEmail_추출_성공() throws NoSuchMethodException {
        // given
        MethodParameter parameter = new MethodParameter(UserParameterInfo.class.getDeclaredMethod("supportAllAnnot",
                UUID.class, String.class, List.class), 1);
        JwtAuthenticationToken authentication = new JwtAuthenticationToken("principal", "credentials",
                new ArrayList<>());
        authentication.setDetails(details);

        securityContext.setAuthentication(authentication);

        // when
        Object resolveValue = userDetailArgumentResolver.resolveArgument(parameter, null, new MockNativeWebRequest(),
                null);

        // then
        assertThat(resolveValue).isEqualTo(authentication.getPrincipal());
    }

    @Test
    void UserEmail_추출_실패_익명사용자() throws NoSuchMethodException {
        // given
        MethodParameter parameter = new MethodParameter(UserParameterInfo.class.getDeclaredMethod("supportAllAnnot",
                UUID.class, String.class, List.class), 1);
        JwtAuthenticationToken authentication = new JwtAuthenticationToken("anonymousUser", "credentials",
                new ArrayList<>());
        authentication.setDetails(details);
        securityContext.setAuthentication(authentication);

        // when
        Object resolveValue = userDetailArgumentResolver.resolveArgument(parameter, null, new MockNativeWebRequest(),
                null);

        // then
        assertThat(resolveValue).isEqualTo("");
    }

    @Test
    void userRole_추출_성공() throws NoSuchMethodException {
        // given
        MethodParameter parameter = new MethodParameter(UserParameterInfo.class.getDeclaredMethod("supportAllAnnot",
                UUID.class, String.class, List.class), 2);
        List<SimpleGrantedAuthority> roles = List.of(new SimpleGrantedAuthority(UserRoleType.USER.name()));
        Authentication auth = new JwtAuthenticationToken("test", "", roles);
        securityContext.setAuthentication(auth);

        // when
        List<UserRoleType> roleList = (List<UserRoleType>) userDetailArgumentResolver.resolveArgument(parameter, null
                , new MockNativeWebRequest(), null);

        // then
        assertThat(roleList).contains(UserRoleType.USER);
    }

    @Test
    void userRole_추출_실패_익명사용자() throws NoSuchMethodException {
        // given
        MethodParameter parameter = new MethodParameter(UserParameterInfo.class.getDeclaredMethod("supportAllAnnot",
                UUID.class, String.class, List.class), 2);
        Authentication auth = new JwtAuthenticationToken("anonymousUser", "", null);
        securityContext.setAuthentication(auth);

        // when
        List<UserRoleType> roleList = (List<UserRoleType>) userDetailArgumentResolver.resolveArgument(parameter, null
                , new MockNativeWebRequest(), null);

        // then
        assertThat(roleList).isEmpty();
    }

}
