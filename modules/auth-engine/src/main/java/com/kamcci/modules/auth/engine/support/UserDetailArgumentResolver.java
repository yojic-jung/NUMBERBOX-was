package com.kamcci.modules.auth.engine.support;

import com.kamcci.modules.auth.control.annotation.UserEmail;
import com.kamcci.modules.auth.control.annotation.UserId;
import com.kamcci.modules.auth.control.annotation.UserRole;
import com.kamcci.modules.auth.control.enumeration.UserRoleType;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.MethodParameter;
import org.springframework.lang.Nullable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Component
@Qualifier("userDetail")
public class UserDetailArgumentResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterAnnotation(UserId.class) != null || parameter.getParameterAnnotation(UserEmail.class) != null || parameter.getParameterAnnotation(UserRole.class) != null;
    }

    @Nullable
    @Override
    public Object resolveArgument(MethodParameter parameter, @Nullable ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, @Nullable WebDataBinderFactory binderFactory) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean hasUserIdAnnot = parameter.getParameterAnnotation(UserId.class) != null;
        boolean hasUserEmailAnnot = parameter.getParameterAnnotation(UserEmail.class) != null;
        boolean isAnonymousUser = authentication.getPrincipal().equals("anonymousUser");

        if(hasUserIdAnnot) {
            // @UserId 처리
            Map<String, Object> details = (Map<String, Object>) authentication.getDetails();
            return isAnonymousUser ? null : UUID.fromString(details.get(UserId.ATTR_NAME).toString());
        } else if(hasUserEmailAnnot) {
            // @UserEmail 처리
            return isAnonymousUser ? "" : authentication.getPrincipal().toString();
        } else {
            // @UserRole 처리
            if(isAnonymousUser) {
                return new ArrayList<UserRoleType>();
            } else {
                List<String> roles = new ArrayList<>();
                authentication.getAuthorities().forEach(authority -> roles.add(authority.getAuthority()));

                List<UserRoleType> roleTypeList = new ArrayList<>();
                for(String role : roles) {
                    UserRoleType roleType = UserRoleType.valueOf(role);
                    roleTypeList.add(roleType);
                }
                return roleTypeList;
            }
        }
    }

}
