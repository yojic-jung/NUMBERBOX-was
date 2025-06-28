package com.kamcci.numberbox.infra.persistence.adapter.repository.auth//package com.kamcci.numberbox.infra.persistence.adapter.repository

import com.kamcci.modules.auth.control.dto.LogoutSuccessEvent
import com.kamcci.numberbox.infra.orm.jpa.adapter.repository.member.MemberRefreshTokenRepository
import com.kamcci.numberbox.infra.redis.adapter.repository.token.RefreshTokenRedisCacheRepository
import org.springframework.context.annotation.Primary
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

/**
 * 로그아웃 성공 후처리
 */
@Primary
@Component
class LogoutSuccessEventListener(
    private val memberRefreshTokenRepo: MemberRefreshTokenRepository,
    private val refreshTokenRedisCacheRepository: RefreshTokenRedisCacheRepository
) {
    @Transactional
    @EventListener
    fun handle(logoutSuccessEvent: LogoutSuccessEvent) {
        // redis 캐싱 삭제
        refreshTokenRedisCacheRepository.evictCache(logoutSuccessEvent.refreshToken)

        // rdb 즉시 삭제
        memberRefreshTokenRepo.deleteByToken(logoutSuccessEvent.refreshToken)
    }
}