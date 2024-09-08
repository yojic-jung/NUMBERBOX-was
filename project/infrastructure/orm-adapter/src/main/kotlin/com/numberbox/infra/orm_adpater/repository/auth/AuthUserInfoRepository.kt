package com.numberbox.infra.orm_adpater.repository.auth

import com.numberbox.infra.orm_adpater.repository.member.MemberRefreshTokenInfoRepository
import com.numberbox.infra.orm_adpater.repository.member.MemberRepository
import com.numberbox.modules.auth.control.dto.AuthUserInfo
import com.numberbox.modules.auth.control.dto.AuthUserRole
import com.numberbox.modules.auth.control.service.JwtRequestUserDetailService
import com.numberbox.modules.auth.control.service.LoginRequestUserDetailService
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class AuthUserInfoRepository(
    private val memberRepository: MemberRepository,
    private val refreshTokenInfoRepository: MemberRefreshTokenInfoRepository,
) : LoginRequestUserDetailService, JwtRequestUserDetailService {

    override fun loadUserByUsername(username: String): AuthUserInfo? {
        val member = memberRepository.findByEmail(username) ?: return null

        val roles = member.role.map { AuthUserRole(it.roleName, it.enabled) }
        return AuthUserInfo(member.email, member.userUniqId, member.password, roles)
    }

    override fun loadUserIdByRefreshToken(token: String): UUID? {
        return refreshTokenInfoRepository.findUserUniqIdByToken(token)
    }
}
