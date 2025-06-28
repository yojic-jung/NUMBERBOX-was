//package com.kamcci.numberbox.infra.orm.jpa.adapter.event.listener
//
//import com.kamcci.modules.auth.control.dto.LogoutSuccessEvent
//import com.kamcci.numberbox.infra.orm.jpa.adapter.mock.MockMemberRefreshTokenRepository
//import org.junit.jupiter.api.Test
//import org.junit.jupiter.api.assertDoesNotThrow
//
//class LogoutSuccessEventListenerTest {
//    private val logoutSuccessEventListener = LogoutSuccessEventListener(MockMemberRefreshTokenRepository())
//
//    @Test
//    fun `로그아웃 성공 이벤트 리스너 검증`() {
//        // given
//        val anyRefreshToken = "new-refresh-token"
//        val loginSuccessEvent = LogoutSuccessEvent(anyRefreshToken)
//
//        // when & then
//        assertDoesNotThrow {
//            logoutSuccessEventListener.handle(loginSuccessEvent)
//        }
//    }
//}