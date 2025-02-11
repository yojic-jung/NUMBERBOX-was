package com.kamcci.modules.auth.dummy;

import com.kamcci.modules.auth.control.dto.AuthUserInfo;
import com.kamcci.modules.auth.control.dto.AuthUserRole;
import com.kamcci.modules.auth.control.enumeration.UserRoleType;
import com.kamcci.modules.auth.engine.dto.AuthUserDetail;
import com.kamcci.modules.auth.engine.dto.JwtAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AuthUserDummyData {
    private AuthUserDummyData() { }

    public static AuthUserInfo getAuthUserInfo() {
        AuthUserRole role = new AuthUserRole("USER", true);
        List<AuthUserRole> roleList = new ArrayList<>();
        roleList.add(role);
        return new AuthUserInfo("username", UUID.randomUUID(), "", roleList);
    }

    public static AuthUserInfo getDisableAuthUserInfo() {
        AuthUserRole role = new AuthUserRole("USER", false);
        List<AuthUserRole> roleList = new ArrayList<>();
        roleList.add(role);
        return new AuthUserInfo("username", UUID.randomUUID(), "", roleList);
    }

    public static AuthUserDetail getAuthUserDetail() {
        return new AuthUserDetail(getAuthUserInfo());
    }

    public static AuthUserDetail getDisableAuthUserDetail() {
        return new AuthUserDetail(getDisableAuthUserInfo());
    }

    public static Authentication getAuthentication() {
        List<SimpleGrantedAuthority> roles = List.of(new SimpleGrantedAuthority(UserRoleType.USER.name()));
        return new JwtAuthenticationToken("", "", roles);
    }

}
