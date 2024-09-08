package com.numberbox.infra.orm.repository.auth

import com.numberbox.infra.orm.base.BaseRepository
import com.numberbox.infra.orm.entity.member.QMemberRefreshTokenEntity.memberRefreshTokenEntity
import com.numberbox.infra.orm.repository.member.MemberRepository
import com.numberbox.modules.auth.control.dto.AuthUserInfo
import com.numberbox.modules.auth.control.dto.AuthUserRole
import com.numberbox.modules.auth.control.service.JwtRequestUserDetailService
import com.numberbox.modules.auth.control.service.LoginRequestUserDetailService
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class AuthUserInfoRepository(
    private val memberRepository: MemberRepository,
) : LoginRequestUserDetailService, JwtRequestUserDetailService, BaseRepository() {

    override fun loadUserByUsername(username: String): AuthUserInfo? {
        val member = memberRepository.findByEmail(username) ?: return null

        val roles = member.role.map { AuthUserRole(it.roleName, it.enabled) }
        return AuthUserInfo(member.email, member.userUniqId, member.password, roles)
    }

    override fun loadUserIdByRefreshToken(token: String): UUID? {
        return queryFactory
            .select(memberRefreshTokenEntity.userUniqId)
            .from(memberRefreshTokenEntity)
            .where(memberRefreshTokenEntity.token.eq(token))
            .orderBy(memberRefreshTokenEntity.id.desc())
            .fetchFirst()
    }
}
