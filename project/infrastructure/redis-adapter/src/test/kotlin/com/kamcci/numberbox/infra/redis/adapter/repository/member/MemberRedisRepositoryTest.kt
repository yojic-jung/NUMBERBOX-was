package com.kamcci.numberbox.infra.redis.adapter.repository.member

import com.kamcci.numberbox.infra.redis.adapter.hash.member.MemberRedisHash
import com.kamcci.numberbox.infra.redis.adapter.hash.member.MemberRoleRedis
import com.kamcci.numberbox.infra.redis.anntation.TCRedisTest
import org.assertj.core.api.AssertionsForClassTypes.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import java.util.*

@TCRedisTest
class MemberRedisRepositoryTest @Autowired constructor(
    private val memberRedisRepository: MemberRedisRepository
) {
    private val id = UUID.randomUUID()
    private val email = "test111@test.com"
    private val password = "anyPassword"
    private val roles = MemberRoleRedis("USER", true)
    private val memberRedisHash = MemberRedisHash(id, email, password, listOf(roles))

    @BeforeEach
    fun init() {
        memberRedisRepository.save(memberRedisHash)
    }

    @Test
    fun `회원 조회`() {
        // when
        val redisHash = memberRedisRepository.findByEmail(email)!!

        // then
        assertThat(redisHash.id).isEqualTo(id)
        assertThat(redisHash.email).isEqualTo(email)
        assertThat(redisHash.password).isEqualTo(password)
        assertThat(redisHash.role[0].roleName).isEqualTo(roles.roleName)
        assertThat(redisHash.role[0].enabled).isEqualTo(roles.enabled)
    }
}