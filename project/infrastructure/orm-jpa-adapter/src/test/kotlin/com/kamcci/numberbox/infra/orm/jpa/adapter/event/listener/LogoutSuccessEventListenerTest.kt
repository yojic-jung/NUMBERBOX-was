package com.kamcci.numberbox.infra.orm.jpa.adapter.event.listener

import com.kamcci.modules.auth.control.dto.LogoutSuccessEvent
import com.kamcci.numberbox.infra.orm.jpa.adapter.annotation.TcDBSpringMockConfigTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.transaction.annotation.Transactional

@TcDBSpringMockConfigTest
class LogoutSuccessEventListenerTest @Autowired constructor(
    private val logoutSuccessEventListener: LogoutSuccessEventListener
) {
    @Transactional
    @Test
    fun `로그아웃 성공 이벤트 리스너 검증`() {
        // given
        val refreshToken = "new-refresh-token"
        val loginSuccessEvent = LogoutSuccessEvent(refreshToken)

        // when & then
        assertDoesNotThrow {
            logoutSuccessEventListener.handle(loginSuccessEvent)
        }
    }
}