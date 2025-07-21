package com.kamcci.numberbox.infra.persistence.adapter.repository.auth

import com.kamcci.modules.auth.control.dto.LoginSuccessEvent
import com.kamcci.numberbox.infra.orm.jpa.adapter.mock.MockMemberRefreshTokenRepository
import com.kamcci.numberbox.infra.orm.jpa.adapter.mock.MockMemberRepositorySupport
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.UUID

class LoginSuccessEventListenerTest {
    lateinit var memberRepository: MockMemberRepositorySupport
    lateinit var memberRefreshTokenRepo: MockMemberRefreshTokenRepository
    lateinit var loginSuccessEventListener: LoginSuccessEventListener

    @BeforeEach
    fun setUp() {
        memberRepository = MockMemberRepositorySupport()
        memberRefreshTokenRepo = MockMemberRefreshTokenRepository()
        loginSuccessEventListener = LoginSuccessEventListener(
            memberRepository,
            memberRefreshTokenRepo,
        )
    }

    @Test
    fun `클리이언트에 이전 refreshToken 존재시 refreshToken 제거`() {
        // given
        val userId = UUID.randomUUID()
        val refreshToken = "any"
        val remainedRefreshToken = "remainToken"
        val event = LoginSuccessEvent(userId, refreshToken, remainedRefreshToken)

        // when
        loginSuccessEventListener.handle(event)

        // then
        assertThat(memberRepository.executeCnt).isOne
        assertThat(memberRefreshTokenRepo.executeCnt).isEqualTo(2)
    }

    @Test
    fun `클리이언트에 이전 refreshToken 미존재시(null)`() {
        // given
        val userId = UUID.randomUUID()
        val refreshToken = "any"
        val remainedRefreshToken = null
        val event = LoginSuccessEvent(userId, refreshToken, remainedRefreshToken)

        // when
        loginSuccessEventListener.handle(event)

        // then
        assertThat(memberRepository.executeCnt).isOne
        assertThat(memberRefreshTokenRepo.executeCnt).isOne
    }

    @Test
    fun `클리이언트에 이전 refreshToken 미존재시(empty)`() {
        // given
        val userId = UUID.randomUUID()
        val refreshToken = "any"
        val remainedRefreshToken = ""
        val event = LoginSuccessEvent(userId, refreshToken, remainedRefreshToken)

        // when
        loginSuccessEventListener.handle(event)

        // then
        assertThat(memberRepository.executeCnt).isOne
        assertThat(memberRefreshTokenRepo.executeCnt).isOne
    }

}