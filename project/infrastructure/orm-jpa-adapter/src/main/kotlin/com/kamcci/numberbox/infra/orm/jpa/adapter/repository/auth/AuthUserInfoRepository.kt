package com.kamcci.numberbox.infra.orm.jpa.adapter.repository.auth

import com.kamcci.modules.auth.control.dto.AuthUserInfo
import com.kamcci.modules.auth.control.dto.AuthUserRole
import com.kamcci.modules.auth.control.service.JwtRequestUserDetailService
import com.kamcci.modules.auth.control.service.LoginRequestUserDetailService
import com.kamcci.modules.logging.control.service.IPAddressService
import com.kamcci.numberbox.infra.orm.jpa.adapter.base.BaseRepository
import com.kamcci.numberbox.infra.orm.jpa.adapter.common.CacheNames.REFRESH_TOKEN
import com.kamcci.numberbox.infra.orm.jpa.adapter.entity.log.QLogClientApiEntity.logClientApiEntity
import com.kamcci.numberbox.infra.orm.jpa.adapter.entity.member.QMemberRefreshTokenEntity.memberRefreshTokenEntity
import com.kamcci.numberbox.infra.orm.jpa.adapter.repository.member.MemberRepositorySupport
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Repository
import java.time.LocalDateTime
import java.util.*

@Repository
class AuthUserInfoRepository(
    private val ipAddressService: IPAddressService,
    private val memberRepositorySupport: MemberRepositorySupport,
) : LoginRequestUserDetailService, JwtRequestUserDetailService, BaseRepository() {

    override fun loadUserByUsername(username: String): AuthUserInfo? {
        val member = memberRepositorySupport.findByEmail(username) ?: return null

        val roles = member.role.map { AuthUserRole(it.roleName, it.enabled) }
        return AuthUserInfo(member.email, member.id, member.password, roles)
    }

    @Cacheable(cacheNames = [REFRESH_TOKEN], key = "#token")
    override fun loadUserIdByRefreshToken(token: String): UUID? {
        return queryFactory
            .select(memberRefreshTokenEntity.memberId)
            .from(memberRefreshTokenEntity)
            .where(memberRefreshTokenEntity.token.eq(token))
            .orderBy(memberRefreshTokenEntity.id.desc())
            .fetchFirst()
    }

    override fun canReCreateRefreshToken(userId: UUID): Boolean {
        val clientIp = ipAddressService.getPublicIPAddress()
        return queryFactory
            .selectOne()
            .from(logClientApiEntity)
            .where(
                logClientApiEntity.memberId.eq(userId),
                logClientApiEntity.ip.eq(clientIp),
                logClientApiEntity.sysCreateTime.gt(LocalDateTime.now().minusMonths(1))
            )
            .fetchFirst() != null
    }
}
