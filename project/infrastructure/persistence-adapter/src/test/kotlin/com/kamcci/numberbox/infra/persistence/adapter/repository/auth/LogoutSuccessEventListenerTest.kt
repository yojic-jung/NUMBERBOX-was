package com.kamcci.numberbox.infra.persistence.adapter.repository.auth

import com.kamcci.modules.auth.control.dto.LogoutSuccessEvent
import com.kamcci.numberbox.infra.orm.jpa.adapter.mock.MockMemberRefreshTokenRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class LogoutSuccessEventListenerTest {
    lateinit var memberRefreshTokenRepo: MockMemberRefreshTokenRepository
    lateinit var logoutSuccessEventListener : LogoutSuccessEventListener

    @BeforeEach
    fun setUp() {
        memberRefreshTokenRepo = MockMemberRefreshTokenRepository()
        logoutSuccessEventListener = LogoutSuccessEventListener(memberRefreshTokenRepo,)
    }

    @Test
    fun `(단순호출) 로그아웃 후처리 성공`() {
        // given
        val event = LogoutSuccessEvent("any")

        // when
        logoutSuccessEventListener.handle(event)

        // then
        assertThat(memberRefreshTokenRepo.executeCnt).isOne
    }

}