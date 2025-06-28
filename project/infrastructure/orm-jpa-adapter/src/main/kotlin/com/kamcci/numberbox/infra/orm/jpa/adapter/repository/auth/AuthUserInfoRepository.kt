package com.kamcci.numberbox.infra.orm.jpa.adapter.repository.auth

import com.kamcci.modules.logging.control.service.IPAddressService
import com.kamcci.numberbox.infra.orm.jpa.adapter.base.BaseRepository
import com.kamcci.numberbox.infra.orm.jpa.adapter.entity.log.QLogClientApiEntity.logClientApiEntity
import com.kamcci.numberbox.infra.orm.jpa.adapter.entity.member.MemberEntity
import com.kamcci.numberbox.infra.orm.jpa.adapter.entity.member.QMemberEntity.memberEntity
import com.kamcci.numberbox.infra.orm.jpa.adapter.entity.member.QMemberRefreshTokenEntity.memberRefreshTokenEntity
import org.springframework.stereotype.Repository
import java.time.LocalDateTime
import java.util.*

@Repository
class AuthUserInfoRepository(
    private val ipAddressService: IPAddressService,
) : BaseRepository() {

    fun findByEmail(username: String): MemberEntity? {
        return queryFactory
            .selectFrom(memberEntity)
            .where(memberEntity.email.eq(username))
            .fetchOne()
    }

    fun loadUserIdByRefreshToken(token: String): UUID? {
        return queryFactory
            .select(memberRefreshTokenEntity.memberId)
            .from(memberRefreshTokenEntity)
            .where(memberRefreshTokenEntity.token.eq(token))
            .orderBy(memberRefreshTokenEntity.id.desc())
            .fetchFirst()
    }

    fun canReCreateRefreshToken(userId: UUID): Boolean {
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
