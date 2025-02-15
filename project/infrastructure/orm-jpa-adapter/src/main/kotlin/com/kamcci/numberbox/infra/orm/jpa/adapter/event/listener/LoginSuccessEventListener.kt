package com.kamcci.numberbox.infra.orm.jpa.adapter.event.listener

import com.kamcci.modules.auth.control.dto.LoginSuccessEvent
import com.kamcci.numberbox.infra.orm.jpa.adapter.factory.member.MemberRefreshTokenFactory
import com.kamcci.numberbox.infra.orm.jpa.adapter.repository.member.MemberRefreshTokenRepository
import com.kamcci.numberbox.infra.orm.jpa.adapter.repository.member.MemberRepositorySupport
import jakarta.transaction.Transactional
import org.springframework.context.event.EventListener
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component
import java.util.*

/**
 * 로그인 성공 후처리
 */
@Component
class LoginSuccessEventListener(
    private val memberRepository: MemberRepositorySupport,
    private val memberRefreshTokenRepo: MemberRefreshTokenRepository,
) {

    companion object {
        private const val FAIL_CNT_ZERO = 0
        private const val HUMAN_STATUS_ENABLE = 0
    }

    @Async
    @Transactional
    @EventListener
    fun handle(loginSuccessEvent: LoginSuccessEvent) {
        val userId: UUID = loginSuccessEvent.userId()
        val refreshToken: String = loginSuccessEvent.refreshToken()
        val remainedRefreshToken: String? = loginSuccessEvent.remainedRefreshToken()

        // 실패 횟수, 휴면 계정 초기화
        memberRepository.updateSuccessUser(userId, FAIL_CNT_ZERO, HUMAN_STATUS_ENABLE)

        // 기존 refreshToken 남아있는 경우 삭제
        remainedRefreshToken
            ?.takeIf { it.isNotEmpty() }
            ?.let { memberRefreshTokenRepo.deleteByToken(it) }

        // 새로운 리프레시 토큰 저장
        val rerfreshTokenEntity = MemberRefreshTokenFactory.getSaveEntity(refreshToken, userId)
        memberRefreshTokenRepo.save(rerfreshTokenEntity)
    }
}
