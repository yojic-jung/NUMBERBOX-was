package com.kamcci.numberbox.infra.orm.event.listener

import com.kamcci.modules.auth.control.dto.LogoutSuccessEvent
import com.kamcci.numberbox.infra.orm.adapter.member.MemberRefreshTokenRepository
import jakarta.transaction.Transactional
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

/**
 * 로그아웃 성공 후처리
 */
@Component
class LogoutSuccessEventListener(
    private val memberRefreshTokenRepo: MemberRefreshTokenRepository
) {
    @Transactional
    @EventListener
    fun handle(logoutSuccessEvent: LogoutSuccessEvent) {
        memberRefreshTokenRepo.deleteByToken(logoutSuccessEvent.refreshToken)
    }
}