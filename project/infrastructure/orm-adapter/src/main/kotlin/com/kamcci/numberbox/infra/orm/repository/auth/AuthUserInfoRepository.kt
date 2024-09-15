package com.kamcci.numberbox.infra.orm.repository.auth

import com.kamcci.modules.auth.control.dto.AuthUserInfo
import com.kamcci.modules.auth.control.dto.AuthUserRole
import com.kamcci.modules.auth.control.service.JwtRequestUserDetailService
import com.kamcci.modules.auth.control.service.LoginRequestUserDetailService
import com.kamcci.numberbox.infra.orm.base.BaseRepository
import com.kamcci.numberbox.infra.orm.entity.member.QMemberRefreshTokenEntity.memberRefreshTokenEntity
import com.kamcci.numberbox.infra.orm.repository.member.MemberRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class AuthUserInfoRepository(
    private val memberRepository: MemberRepository,
) : LoginRequestUserDetailService, JwtRequestUserDetailService, BaseRepository() {

    override fun loadUserByUsername(username: String): AuthUserInfo? {
        val member = memberRepository.findByEmail(username) ?: return null

        val roles = member.role.map { AuthUserRole(it.roleName, it.enabled) }
        return AuthUserInfo(member.email, member.id, member.password, roles)
    }

    override fun loadUserIdByRefreshToken(token: String): UUID? {
        return queryFactory
            .select(memberRefreshTokenEntity.memberId)
            .from(memberRefreshTokenEntity)
            .where(memberRefreshTokenEntity.token.eq(token))
            .orderBy(memberRefreshTokenEntity.id.desc())
            .fetchFirst()
    }
}
