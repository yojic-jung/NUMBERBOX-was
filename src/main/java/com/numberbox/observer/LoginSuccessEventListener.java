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

/**
 * 로그인 성공 후처리
 */
@Component
public class LoginSuccessEventListener {
    private static final int FAIL_CNT_ZERO = 0;
    private static final int HUMAN_STATUS_ENABLE = 0;

    private final MembersRepository membersRepo;
    private final RefreshTokenInfoRepository refreshTokenInfoRepo;

    public LoginSuccessEventListener(MembersRepository membersRepo, RefreshTokenInfoRepository refreshTokenInfoRepo) {
        this.membersRepo = membersRepo;
        this.refreshTokenInfoRepo = refreshTokenInfoRepo;
    }

    @Transactional
    @EventListener
    public void handle(LoginSuccessEvent loginSuccessEvent) {
        final UUID userUniqId = loginSuccessEvent.userId();
        final String refreshToken = loginSuccessEvent.refreshToken();
        final String remainedRefreshToken = loginSuccessEvent.remainedRefreshToken();

        // 로그인 시간, 실패 횟수, 휴면 계정 초기화
        LocalDateTime lastLoginTime = LocalDateTime.now();
        membersRepo.updateByUserUniqId(userUniqId, lastLoginTime, FAIL_CNT_ZERO, HUMAN_STATUS_ENABLE);

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
