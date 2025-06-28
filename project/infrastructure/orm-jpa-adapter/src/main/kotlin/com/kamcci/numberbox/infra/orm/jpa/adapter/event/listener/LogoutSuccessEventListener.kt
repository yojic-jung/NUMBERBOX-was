//package com.kamcci.numberbox.infra.orm.jpa.adapter.event.listener
//
//import com.kamcci.modules.auth.control.dto.LogoutSuccessEvent
//import com.kamcci.numberbox.infra.orm.jpa.adapter.redis.repository.token.RefreshTokenRedisCacheRepository
//import com.kamcci.numberbox.infra.orm.jpa.adapter.repository.member.MemberRefreshTokenRepository
//import jakarta.transaction.Transactional
//import org.springframework.context.event.EventListener
//import org.springframework.stereotype.Component
//
///**
// * 로그아웃 성공 후처리
// */
//@Component
//class LogoutSuccessEventListener(
//    private val memberRefreshTokenRepo: MemberRefreshTokenRepository,
//    private val refreshTokenRedisCacheRepository: RefreshTokenRedisCacheRepository
//) {
//    @Transactional
//    @EventListener
//    fun handle(logoutSuccessEvent: LogoutSuccessEvent) {
//        refreshTokenRedisCacheRepository.evictCache(logoutSuccessEvent.refreshToken)
//        memberRefreshTokenRepo.deleteByToken(logoutSuccessEvent.refreshToken)
//    }
//}