package com.numberbox.modules.auth.engine.dto;

import com.numberbox.modules.auth.control.dto.AuthUserInfo;
import com.numberbox.modules.auth.control.dto.AuthUserRole;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import static com.numberbox.modules.auth.control.config.AuthConstantConfig.ROLE_PREFIX;

/**
 * Def. 서버에서 전달된 사용자 인증 정보
 */
public class AuthUserDetail extends User {

    private final UUID userId;

    public UUID getUserId() {
        return this.userId;
    }

    public AuthUserDetail(AuthUserInfo authUserInfo) {
        super(authUserInfo.username(), authUserInfo.password(), enableCheck(authUserInfo.roles()), true, true, true,
                takeGrantedAuthority(authUserInfo.roles()));
        this.userId = authUserInfo.userId();
    }

    private static List<GrantedAuthority> takeGrantedAuthority(List<AuthUserRole> roles) {
        List<GrantedAuthority> list = new ArrayList<>();
        roles.forEach(userRole -> list.add(new SimpleGrantedAuthority(ROLE_PREFIX + userRole.roleName())));
        return list;
    }

    // enabled의 경우 컬렉션 객체 타입을 갖지 못함(User.class), 하나라도 enabled가 true라면 true로 구현
    private static boolean enableCheck(List<AuthUserRole> roles) {
        for (AuthUserRole role : roles) {
            if (role.enabled()) {
                return true;
            }
        }
        return false;
    }

}