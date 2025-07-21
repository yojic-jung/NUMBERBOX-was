package com.kamcci.numberbox.infra.persistence.adapter.repository.auth

import com.kamcci.modules.auth.control.dto.AuthUserInfo
import com.kamcci.modules.auth.control.dto.AuthUserRole
import com.kamcci.modules.auth.control.service.JwtRequestUserDetailService
import com.kamcci.modules.auth.control.service.LoginRequestUserDetailService
import com.kamcci.numberbox.infra.orm.jpa.adapter.repository.auth.AuthUserInfoRepository
import com.kamcci.numberbox.infra.redis.adapter.hash.member.MemberRedisHash
import com.kamcci.numberbox.infra.redis.adapter.hash.member.MemberRoleRedis
import com.kamcci.numberbox.infra.redis.adapter.repository.member.MemberRedisRepository
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Repository
import java.util.*

@Primary
@Repository
class AuthUserInfoPersitenceRepository(
    private val memberRedisRepository: MemberRedisRepository,
    private val authUserInfoRepository: AuthUserInfoRepository,
) : LoginRequestUserDetailService, JwtRequestUserDetailService {

    override fun loadUserByUsername(username: String): AuthUserInfo? {
        // 캐싱 존재시 캐싱 정보 반환
        val memberRedisHash = memberRedisRepository.findByEmail(username)
        if (memberRedisHash != null) {
            val roles = memberRedisHash.role.map { AuthUserRole(it.roleName, it.enabled) }
            return AuthUserInfo(memberRedisHash.email, memberRedisHash.id, memberRedisHash.password, roles)
        }

        // 캐싱 미존재시 rdb 정보 반환
        val memberEntity = authUserInfoRepository.findByEmail(username)
        if (memberEntity != null) {
            val memberHash = MemberRedisHash(
                memberEntity.id!!,
                memberEntity.email,
                memberEntity.password,
                memberEntity.role.map { MemberRoleRedis(it.roleName, it.enabled) }
            )
            memberRedisRepository.save(memberHash)

            val roles = memberEntity.role.map { AuthUserRole(it.roleName, it.enabled) }
            return AuthUserInfo(memberEntity.email, memberEntity.id, memberEntity.password, roles)
        }
        return null
    }


    override fun loadUserIdByRefreshToken(token: String): UUID? {
        return authUserInfoRepository.loadUserIdByRefreshToken(token)
    }

    override fun canReCreateRefreshToken(userId: UUID): Boolean =
        authUserInfoRepository.canReCreateRefreshToken(userId)
}
