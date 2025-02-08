package com.kamcci.numberbox.infra.orm.jpa.adapter.event.listener

import com.kamcci.modules.auth.control.dto.LoginSuccessEvent
import com.kamcci.numberbox.infra.orm.jpa.adapter.annotation.TcDBSpringMockConfigTest
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.transaction.annotation.Transactional
import java.util.*

@TcDBSpringMockConfigTest
class LoginSuccessEventListenerTest @Autowired constructor(
    private val loginSuccessEventListener: LoginSuccessEventListener
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
            // when & then
            loginSuccessEventListener.handle(loginSuccessEvent)
        }
    }

}