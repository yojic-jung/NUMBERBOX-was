//package com.kamcci.numberbox.infra.orm.jpa.adapter.event.listener
//
//import com.kamcci.modules.auth.control.dto.LoginSuccessEvent
//import com.kamcci.numberbox.infra.orm.jpa.adapter.mock.MockMemberRefreshTokenRepository
//import com.kamcci.numberbox.infra.orm.jpa.adapter.mock.MockMemberRepositorySupport
//import org.assertj.core.api.Assertions.assertThat
//import org.junit.jupiter.api.Test
//import java.util.*
//
//class LoginSuccessEventListenerTest {
//
//    companion object {
//        val ANY_MEMBER_ID = UUID.fromString("10ca3122-cda8-ea4d-9bc7-037cb86fdb20")
//        const val ANY_REFRESH_TOKEN = "new-refresh-token"
//    }
//
//    @Test
//    fun `로그인 성공 이벤트 리스너 검증`() {
//        // given - 리프레시 토큰 존재, empty, null에 대한 모든 테스트
//        val remainedRefreshTokens = listOf("any", "", null)
//
//        for (remainedRefreshToken in remainedRefreshTokens) {
//            // 테스트 더블 설정
//            val memberRefreshTokenRepository = MockMemberRefreshTokenRepository()
//            val loginSuccessEventListener =
//                LoginSuccessEventListener(MockMemberRepositorySupport(), memberRefreshTokenRepository)
//            // 이벤트 객체 설정
//            val loginSuccessEvent = LoginSuccessEvent(ANY_MEMBER_ID, ANY_REFRESH_TOKEN, remainedRefreshToken)
//
//            // when
//            loginSuccessEventListener.handle(loginSuccessEvent)
//
//            // then
//            assertThat(memberRefreshTokenRepository.executeCnt).isOne()
//        }
//    }
//
//}