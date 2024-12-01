package com.kamcci.numberbox.infra.orm.jpa.adapter.event.listener

import com.kamcci.modules.auth.control.dto.LogoutSuccessEvent
import com.kamcci.numberbox.infra.orm.jpa.adapter.annotation.TcDBSpringMockConfigTest
import com.kamcci.numberbox.infra.orm.jpa.adapter.repository.member.MemberRefreshTokenJpaRepository
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationEventPublisher
import org.springframework.transaction.annotation.Transactional

@TcDBSpringMockConfigTest
class LogoutSuccessEventListenerTest @Autowired constructor(
    private val eventPublisher: ApplicationEventPublisher,
    @Autowired
    private val memberRefreshTokenRepo: MemberRefreshTokenJpaRepository
) {
    @Transactional
    @Test
    fun `로그아웃 성공 이벤트 리스너 검증`() {
        // given
        val refreshToken = "new-refresh-token"
        val loginSuccessEvent = LogoutSuccessEvent(refreshToken)

        // when
        eventPublisher.publishEvent(loginSuccessEvent)

        // then
        Mockito.verify(memberRefreshTokenRepo).deleteByToken(refreshToken)
    }
}