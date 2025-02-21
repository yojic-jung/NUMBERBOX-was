package com.kamcci.modules.auth.sample;

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

public class AuthUserSampleData {
    // 테스트용 클라이언트 id
    public static final UUID AUTH_USER_ID = UUID.fromString("11cf5466-cda8-ea4d-9bc7-037cb86fdb20");

    private AuthUserSampleData() { }

    public static AuthUserInfo getAuthUserInfo() {
        AuthUserRole role = new AuthUserRole("USER", true);
        List<AuthUserRole> roleList = new ArrayList<>();
        roleList.add(role);
        return new AuthUserInfo("username", AUTH_USER_ID, "", roleList);
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
