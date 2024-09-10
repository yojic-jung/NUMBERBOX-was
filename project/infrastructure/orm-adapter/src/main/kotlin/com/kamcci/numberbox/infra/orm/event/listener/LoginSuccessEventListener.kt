package com.kamcci.numberbox.infra.orm.event.listener

import com.kamcci.numberbox.infra.orm.factory.member.MemberRefreshTokenFactory
import com.kamcci.numberbox.infra.orm.repository.member.MemberRefreshTokenRepository
import com.kamcci.numberbox.infra.orm.repository.member.MemberRepositoryImpl
import com.kamcci.modules.auth.control.dto.LoginSuccessEvent
import jakarta.transaction.Transactional
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component
import java.util.*

/**
 * 로그인 성공 후처리
 */
@Component
class LoginSuccessEventListener(
    private val memberRepository: MemberRepositoryImpl,
    private val memberRefreshTokenFactory: MemberRefreshTokenFactory,
    private val memberRefreshTokenRepo: MemberRefreshTokenRepository,
) {

    companion object {
        private const val FAIL_CNT_ZERO = 0
        private const val HUMAN_STATUS_ENABLE = 0
    }

    @Transactional
    @EventListener
    fun handle(loginSuccessEvent: LoginSuccessEvent) {
        val userUniqId: UUID = loginSuccessEvent.userId()
        val refreshToken: String = loginSuccessEvent.refreshToken()
        val remainedRefreshToken: String? = loginSuccessEvent.remainedRefreshToken()

        // 실패 횟수, 휴면 계정 초기화
        memberRepository.updateSuccessUser(userUniqId, FAIL_CNT_ZERO, HUMAN_STATUS_ENABLE)

        // 기존 refreshToken 남아있는 경우 삭제
        remainedRefreshToken?.takeIf { it.isNotEmpty() }?.let {
            memberRefreshTokenRepo.deleteByToken(it)
        }

        // 새로운 리프레시 토큰 저장
        val rerfreshTokenEntity = memberRefreshTokenFactory.getSaveEntity(refreshToken, userUniqId)
        memberRefreshTokenRepo.save(rerfreshTokenEntity)
    }
}
