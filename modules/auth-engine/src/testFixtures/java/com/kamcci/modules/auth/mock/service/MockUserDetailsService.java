package com.kamcci.modules.auth.mock.service;

import com.kamcci.modules.auth.control.dto.AuthUserInfo;
import com.kamcci.modules.auth.control.dto.AuthUserRole;
import com.kamcci.modules.auth.engine.dto.AuthUserDetail;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.kamcci.modules.auth.constant.MockAuthTestConstant.FAIL_STRING;
import static com.kamcci.modules.auth.constant.MockAuthTestConstant.NULL_USER;
import static com.kamcci.modules.auth.sample.AuthUserSampleData.getAuthUserDetail;

public class MockUserDetailsService implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if(FAIL_STRING.equals(username)) {
            AuthUserRole role = new AuthUserRole("USER", false);
            List<AuthUserRole> roleList = new ArrayList<>();
            roleList.add(role);
            return new AuthUserDetail(new AuthUserInfo("username", UUID.randomUUID(), "", roleList));
        } else if(NULL_USER.equals(username)) {
            return null;
        }
        return getAuthUserDetail();
    }
}
