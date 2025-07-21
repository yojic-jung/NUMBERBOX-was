package com.kamcci.numberbox.infra.persistence.adapter.repository.auth

import com.kamcci.numberbox.infra.orm.jpa.adapter.entity.member.MemberEntity
import com.kamcci.numberbox.infra.orm.jpa.adapter.entity.member.MemberRoleEntity
import com.kamcci.numberbox.infra.orm.jpa.adapter.mock.MockAuthUserInfoRepository
import com.kamcci.numberbox.infra.orm.jpa.adapter.repository.auth.AuthUserInfoRepository
import com.kamcci.numberbox.infra.redis.adapter.hash.member.MemberRedisHash
import com.kamcci.numberbox.infra.redis.adapter.hash.member.MemberRoleRedis
import com.kamcci.numberbox.infra.redis.adapter.repository.member.MemberRedisRepository
import com.kamcci.numberbox.infra.redis.mock.MockMemberRedisRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.UUID

class AuthUserInfoPersitenceRepositoryTest {
    lateinit var memberRedisRepository: MockMemberRedisRepository
    lateinit var authUserInfoRepository: MockAuthUserInfoRepository
    lateinit var authUserInfoPersitenceRepository : AuthUserInfoPersitenceRepository

    @BeforeEach
    fun setUp() {
        memberRedisRepository = MockMemberRedisRepository()
        authUserInfoRepository = MockAuthUserInfoRepository()
        authUserInfoPersitenceRepository= AuthUserInfoPersitenceRepository(memberRedisRepository, authUserInfoRepository)
    }

    @Test
    fun `사용자 정보 캐시 DB에서 조회`() {
        // given - 캐시 DB에 정보 저장
        val userId = UUID.randomUUID()
        val email = "any"
        val password = "anyPW"
        val roles = listOf(MemberRoleRedis("USER", true), MemberRoleRedis("ADMIN", false))
        val cachedData = MemberRedisHash(userId, email, password, roles)
        memberRedisRepository.dataStore.put(userId, cachedData)

        // when
        val result = authUserInfoPersitenceRepository.loadUserByUsername(email)

        // then
        assertThat(result).isNotNull
        assertThat(result!!.username).isEqualTo(email)
        assertThat(result.userId).isEqualTo(userId)
        assertThat(result.password).isEqualTo(password)
        assertThat(result.roles.size).isEqualTo(roles.size)
        val expectedRoles = roles.map { it.roleName to it.enabled }.toSet()
        val actualRoles = result.roles.map { it.roleName to it.enabled }.toSet()
        assertThat(actualRoles).isEqualTo(expectedRoles)
    }


    @Test
    fun `사용자 정보 RDB에서 조회`() {
        // given - RDB에 정보 저장
        val email = "any"
        val userId = UUID.randomUUID()
        val password = "anyPW"
        val role: MutableList<MemberRoleEntity> = mutableListOf(
            MemberRoleEntity().apply {
                this.enabled = enabled
                this.roleName = "USER"
            }
        )
        
        val memberEneity = MemberEntity().apply {
            this.id = userId
            this.email = email
            this.password = password
            this.role = role
        }
        authUserInfoRepository.dataStore.put(email, memberEneity)

        // when
        val result = authUserInfoPersitenceRepository.loadUserByUsername(email)

        // then
        assertThat(result).isNotNull
        assertThat(result!!.userId).isEqualTo(userId)
        assertThat(result!!.username).isEqualTo(email)
        assertThat(result.password).isEqualTo(password)
    }

    @Test
    fun `사용자 정보 미존재`() {
        // when
        val result = authUserInfoPersitenceRepository.loadUserByUsername("any")

        // then
        assertThat(result).isNull()
    }
    
    @Test
    fun `(단순호출) 리프레시 토큰 조회 - 성공`() {
        // given
        val token = "any"

        // when
        authUserInfoPersitenceRepository.loadUserIdByRefreshToken(token)

        // then
        assertThat(authUserInfoRepository.executeCnt).isOne
    }

    @Test
    fun `(단순호출) 리프레시 토큰 재발급 가능 여부 조회 - 성공`() {
        // given
        val userId = UUID.randomUUID()

        // when
        authUserInfoPersitenceRepository.canReCreateRefreshToken(userId)

        // then
        assertThat(authUserInfoRepository.executeCnt).isOne
    }

}