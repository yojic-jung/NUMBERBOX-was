package com.kamcci.modules.auth.engine.dto;

import com.kamcci.modules.auth.control.dto.AuthUserInfo;
import com.kamcci.modules.auth.control.dto.AuthUserRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class AuthUserDetailTest {
    // 테스트 데이터
    AuthUserInfo user;
    List<AuthUserRole> roleList;

    @BeforeEach
    void 테스트데이터_초기화() {
        roleList = new ArrayList<>();
        roleList.add(new AuthUserRole("ROLE_USER", true));
        user = new AuthUserInfo("username", UUID.randomUUID(), "", roleList);
    }

    @Test
    void equals_성공() {
        AuthUserDetail authDetail = new AuthUserDetail(user);
        AuthUserDetail compareDetail = new AuthUserDetail(user);

        assertThat(authDetail).isEqualTo(compareDetail);
    }

    @Test
    void equals_실패() {
        UUID uuid = UUID.randomUUID();
        AuthUserDetail authDetail = new AuthUserDetail(user);
        AuthUserDetail compareDetail = new AuthUserDetail(new AuthUserInfo("username1", uuid, "", roleList));
        AuthUserDetail compareDetail2 = new AuthUserDetail(new AuthUserInfo("username", UUID.randomUUID(), "",
                roleList));
        AuthUserDetail compareDetail3 = new AuthUserDetail(new AuthUserInfo("username2", UUID.randomUUID(), "",
                roleList));

        assertThat(authDetail).isNotEqualTo(compareDetail);
        assertThat(authDetail).isNotEqualTo(compareDetail2);
        assertThat(authDetail).isNotEqualTo(compareDetail3);
    }

    @Test
    void equals_실패_디테일_아님() {
        AuthUserDetail jwtToken = new AuthUserDetail(user);
        String token = "";
        assertThat(jwtToken).isNotEqualTo(token);
    }
}