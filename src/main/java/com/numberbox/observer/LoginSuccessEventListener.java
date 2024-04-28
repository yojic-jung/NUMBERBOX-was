package com.numberbox.observer;

import com.numberbox.jwt.entity.RefreshTokenInfo;
import com.numberbox.jwt.repository.RefreshTokenInfoRepository;
import com.numberbox.members.repository.MembersRepository;
import com.numberbox.security.dto.LoginSuccessEvent;
import jakarta.transaction.Transactional;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
public class LoginSuccessEventListener {
    private final MembersRepository membersRepo;
    private final RefreshTokenInfoRepository refreshTokenInfoRepo;

    public LoginSuccessEventListener(MembersRepository membersRepo, RefreshTokenInfoRepository refreshTokenInfoRepo) {
        this.membersRepo = membersRepo;
        this.refreshTokenInfoRepo = refreshTokenInfoRepo;
    }

    @Transactional
    @EventListener
    public void handle(LoginSuccessEvent loginSuccessEvent) {
        UUID userUniqId = loginSuccessEvent.userId();
        String refreshToken = loginSuccessEvent.refreshToken();
        String remainedRefreshToken = loginSuccessEvent.remainedRefreshToken();

        // 마지막 로그인 시간 업데이트
        membersRepo.initLastLoginDate(userUniqId, LocalDateTime.now());
        // 휴먼 상태 해제
        membersRepo.initHumanStatus(loginSuccessEvent.userId());

        // 기존 refreshToken 남아있는 경우 삭제
        if (remainedRefreshToken != null && !remainedRefreshToken.isEmpty()) {
            refreshTokenInfoRepo.deleteByToken(remainedRefreshToken);
        }

        // 새로운 리프레시 토큰 저장
        RefreshTokenInfo saveToken =
                RefreshTokenInfo.builder().token(refreshToken).userUniqId(userUniqId).build();
        refreshTokenInfoRepo.save(saveToken);
    }
}
