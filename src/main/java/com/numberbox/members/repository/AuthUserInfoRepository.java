package com.numberbox.members.repository;

import com.numberbox.members.entity.Members;
import com.numberbox.members.entity.MembersRole;
import com.numberbox.security.dto.AuthUserInfo;
import com.numberbox.security.dto.AuthUserRole;
import com.numberbox.security.service.LoginRequestUserDetailService;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class AuthUserInfoRepository implements LoginRequestUserDetailService {
    MembersRepository membersRepository;

    public AuthUserInfoRepository(MembersRepository membersRepository) {
        this.membersRepository = membersRepository;
    }

    @Override
    public AuthUserInfo loadUserByUsername(String username) {
        Members member = membersRepository.findByEmail(username);

        List<AuthUserRole> roles = new ArrayList<>();
        for (MembersRole role : member.getRole()) {
            roles.add(new AuthUserRole(role.getRoleName(), role.isEnabled()));
        }
        return new AuthUserInfo(member.getEmail(), member.getUserUniqId(), member.getPassword(), roles);
    }
}
