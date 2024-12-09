package com.kamcci.numberbox.infra.orm.jpa.adapter.event.listener

import com.kamcci.modules.auth.control.dto.LoginSuccessEvent
import com.kamcci.numberbox.infra.orm.jpa.adapter.annotation.TcDBSpringMockConfigTest
import com.kamcci.numberbox.infra.orm.jpa.adapter.repository.member.MemberRefreshTokenJpaRepository
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.Mockito.times
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationEventPublisher
import org.springframework.transaction.annotation.Transactional
import java.util.*

@TcDBSpringMockConfigTest
class LoginSuccessEventListenerTest @Autowired constructor(
    private val eventPublisher: ApplicationEventPublisher,
    @Autowired
    private val memberRefreshTokenRepo: MemberRefreshTokenJpaRepository
) {
    @Transactional
    @Test
    fun `로그인 성공 이벤트 리스너 검증`() {
        // given
        val memberId = UUID.fromString("10ca3122-cda8-ea4d-9bc7-037cb86fdb20")
        val refreshToken = "new-refresh-token"
        val remainedRefreshTokens = listOf("old-refresh-token", "", null)

        for (remainedRefreshToken in remainedRefreshTokens) {
            val loginSuccessEvent = LoginSuccessEvent(memberId, refreshToken, remainedRefreshToken)
            // when
            eventPublisher.publishEvent(loginSuccessEvent)
        }

        // then
        Mockito.verify(memberRefreshTokenRepo, times(3)).save(
            Mockito.argThat { it.token == refreshToken && it.memberId == memberId }
        )
    }

}