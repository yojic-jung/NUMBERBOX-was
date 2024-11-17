package com.kamcci.numberbox.common.error.service;

import com.kamcci.numberbox.common.error.port.in.LoginFailureUseCase;
import com.kamcci.numberbox.members.entity.Members;
import com.kamcci.numberbox.members.repository.MembersRepository;
import com.kamcci.numberbox.members.repository.MembersRoleRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class LoginFailureService implements LoginFailureUseCase {
    // 계정 비활성화 실패 카운트 기준
    private static final int DISABLE_CRITERIA = 4;

    private MembersRepository membersRepository;
    private MembersRoleRepository membersRoleRepository;

    public LoginFailureService(MembersRepository membersRepository, MembersRoleRepository membersRoleRepository){
        this.membersRepository= membersRepository;
        this.membersRoleRepository = membersRoleRepository;
    }
    
    @Override
    @Transactional
    public boolean disableUserIfFailCountOver(String userEmail) {
        final Members members = membersRepository.findByEmail(userEmail);
        final UUID userUniqId = members.getUserUniqId();

        final int failCount = members.getFailCount();
        if (failCount == DISABLE_CRITERIA) {
            membersRoleRepository.disableMember(userUniqId);
        }
        membersRepository.initFailCountAndLastFailTime(userUniqId, failCount + 1, LocalDateTime.now());

        return failCount >= DISABLE_CRITERIA;
    }

    @Override
    @Transactional
    public boolean ableUserIfDisableTimeOver(String userEmail) {
        final Members members = membersRepository.findByEmail(userEmail);
        final UUID userUniqId = members.getUserUniqId();

        final LocalDateTime lastFailTime = members.getLastFailTime();
        boolean isAfter15m = lastFailTime.plusMinutes(15).isBefore(LocalDateTime.now());

        // 15분 지나면 enabled=true, failCount=0로 변경(로그인 시도 가능하도록)
        if (isAfter15m) {
            membersRoleRepository.ableMember(userUniqId);
            membersRepository.initFailCountAndLastFailTime(userUniqId, 0, LocalDateTime.now());
        } else {
            membersRepository.initLastFailTime(userUniqId, LocalDateTime.now());
        }
        return isAfter15m;
    }
}
