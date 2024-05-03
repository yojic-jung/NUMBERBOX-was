package com.numberbox.members.repository;

import com.numberbox.jwt.repository.RefreshTokenInfoRepository;
import com.numberbox.members.entity.Members;
import com.numberbox.members.entity.MembersRole;
import com.numberbox.security.dto.AuthUserInfo;
import com.numberbox.security.dto.AuthUserRole;
import com.numberbox.security.service.JwtRequestUserDetailService;
import com.numberbox.security.service.LoginRequestUserDetailService;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
public class AuthUserInfoRepository implements LoginRequestUserDetailService, JwtRequestUserDetailService {
    MembersRepository membersRepository;
    RefreshTokenInfoRepository refreshTokenInfoRepository;

    public AuthUserInfoRepository(MembersRepository membersRepository,
                                  RefreshTokenInfoRepository refreshTokenInfoRepository) {
        this.membersRepository = membersRepository;
        this.refreshTokenInfoRepository = refreshTokenInfoRepository;
    }

    @Override
    public AuthUserInfo loadUserByUsername(String username) {
        Members member = membersRepository.findByEmail(username);

        if (member == null) return null;

        List<AuthUserRole> roles = new ArrayList<>();
        for (MembersRole role : member.getRole()) {
            roles.add(new AuthUserRole(role.getRoleName(), role.isEnabled()));
        }
        return new AuthUserInfo(member.getEmail(), member.getUserUniqId(), member.getPassword(), roles);
    }

    @Override
    public UUID loadUserIdByRefreshToken(String token) {
        return refreshTokenInfoRepository.finUserUniqIdByToken(token);
    }
}
