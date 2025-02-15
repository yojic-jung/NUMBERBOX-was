package com.kamcci.numberbox.infra.orm.jpa.adapter.event.listener

import com.kamcci.modules.auth.control.dto.LoginSuccessEvent
import com.kamcci.numberbox.infra.orm.jpa.adapter.stub.MockMemberRefreshTokenRepository
import com.kamcci.numberbox.infra.orm.jpa.adapter.stub.MockMemberRepositorySupport
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.*

class LoginSuccessEventListenerTest {
    private lateinit var loginSuccessEventListener: LoginSuccessEventListener

    @BeforeEach
    fun `초기화`() {
        loginSuccessEventListener =
            LoginSuccessEventListener(MockMemberRepositorySupport(), MockMemberRefreshTokenRepository())

    }

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