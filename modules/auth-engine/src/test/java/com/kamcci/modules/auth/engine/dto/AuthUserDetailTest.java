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
    private AuthUserInfo user;
    private List<AuthUserRole> roleList;

    @BeforeEach
    void 테스트데이터_초기화() {
        roleList = new ArrayList<>();
        roleList.add(new AuthUserRole("ROLE_USER", true));
        user = new AuthUserInfo("username", UUID.randomUUID(), "", roleList);
    }

    @Test
    void equals_성공() {
        // given - user 정보 동일하게 설정
        AuthUserDetail authDetail = new AuthUserDetail(user);
        AuthUserDetail compareDetail = new AuthUserDetail(user);

        // then
        assertThat(authDetail).isEqualTo(compareDetail);
    }

    @Test
    void equals_실패() {
        // given - user 정보 각기 다르게 설정
        UUID uuid = UUID.randomUUID();
        AuthUserDetail authDetail = new AuthUserDetail(user);
        AuthUserDetail compareDetail = new AuthUserDetail(new AuthUserInfo("username1", uuid, "", roleList));
        AuthUserDetail compareDetail2 = new AuthUserDetail(new AuthUserInfo("username", UUID.randomUUID(), "",
                roleList));
        AuthUserDetail compareDetail3 = new AuthUserDetail(new AuthUserInfo("username2", UUID.randomUUID(), "",
                roleList));

        // then
        assertThat(authDetail).isNotEqualTo(compareDetail).isNotEqualTo(compareDetail2).isNotEqualTo(compareDetail3);
    }

    @Test
    void equals_실패_디테일_아님() {
        // given - 서로 다른 클래스 타입
        AuthUserDetail userDtail = new AuthUserDetail(user);
        String token = "";

        // then - 클래스 타입 다르면 불일치
        assertThat(userDtail).isNotEqualTo(token);
    }

    @Test
    void userDetail_hashcode() {
        // given - user 정보 같게 설정
        AuthUserDetail userDetail = new AuthUserDetail(user);
        AuthUserDetail compareDetail = new AuthUserDetail(user);

        // then
        assertThat(userDetail).hasSameHashCodeAs(compareDetail);
    }
}