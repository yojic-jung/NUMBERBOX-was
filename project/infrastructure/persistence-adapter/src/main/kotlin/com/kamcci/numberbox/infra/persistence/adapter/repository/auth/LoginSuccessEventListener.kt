package com.kamcci.numberbox.infra.persistence.adapter.repository.auth // package com.kamcci.numberbox.infra.persistence.adapter.repository

import com.kamcci.modules.auth.control.dto.LoginSuccessEvent
import com.kamcci.numberbox.infra.orm.jpa.adapter.entity.member.MemberRefreshTokenEntity
import com.kamcci.numberbox.infra.orm.jpa.adapter.repository.member.MemberRefreshTokenRepository
import com.kamcci.numberbox.infra.orm.jpa.adapter.repository.member.MemberRepositorySupport
import org.springframework.context.annotation.Primary
import org.springframework.context.event.EventListener
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.util.*

/**
 * 로그인 성공 후처리
 */
@Primary
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
            .takeIf { !it.isNullOrEmpty() }
            ?.let {
                memberRefreshTokenRepo.deleteByToken(it)
            }

        // 새로운 리프레시 토큰 저장
        val refreshTokenEntity = MemberRefreshTokenEntity().apply {
            token = refreshToken
            this.memberId = userId
        }
        memberRefreshTokenRepo.save(refreshTokenEntity)
    }
}
