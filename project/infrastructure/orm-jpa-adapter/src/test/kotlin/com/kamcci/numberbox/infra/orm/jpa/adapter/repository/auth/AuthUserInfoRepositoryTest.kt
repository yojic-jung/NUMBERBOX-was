package com.kamcci.numberbox.infra.orm.jpa.adapter.repository.auth

import com.kamcci.numberbox.infra.orm.jpa.adapter.annotation.TcDBJpaTest
import com.kamcci.numberbox.infra.orm.jpa.adapter.dummy.member.MemberEntityDummy.MEMBER_EMAIL
import com.kamcci.numberbox.infra.orm.jpa.adapter.dummy.member.MemberEntityDummy.NOT_EXIST_MEMBER_EMAIL
import com.kamcci.numberbox.infra.orm.jpa.adapter.dummy.member.MemberRefreshTokenEntityDummy
import com.kamcci.numberbox.infra.orm.jpa.adapter.dummy.member.MemberRefreshTokenEntityDummy.getExpiredTokenEntity
import com.kamcci.numberbox.infra.orm.jpa.adapter.repository.log.LogClientApiRepository
import com.kamcci.numberbox.infra.orm.jpa.adapter.sample.common.CommonSampleData.getClientLoggingInfoEventDto
import jakarta.persistence.EntityManager
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import java.util.*

@TcDBJpaTest
class AuthUserInfoRepositoryTest @Autowired constructor(
    private val em: EntityManager,
    private val authUserInfoRepository: AuthUserInfoRepository,
    private val logClientApiRepository: LogClientApiRepository,
) {
    @Test
    fun `멤버엔티티 조회 - 존재`() {
        // given
        val email = MEMBER_EMAIL

        // when
        val authUser = authUserInfoRepository.loadUserByUsername(email)

        // then
        assertThat(authUser?.username).isEqualTo(email)
    }

    @Test
    fun `멤버엔티티 조회 - 미존재`() {
        // given
        val email = NOT_EXIST_MEMBER_EMAIL

        // when
        val authUser = authUserInfoRepository.loadUserByUsername(email)

        // then
        assertThat(authUser).isNull()
    }

    @Test
    fun `멤버 엔티티 토큰으로 조회 - 성공`() {
        // given
        val existEntity = getExpiredTokenEntity()
        val token = existEntity.token

        // when
        val memberId = authUserInfoRepository.loadUserIdByRefreshToken(token)

        // then
        assertThat(memberId).isEqualTo(existEntity.memberId)
    }

    @Test
    fun `리프레시 토큰 재발급 여부 - true`() {
        // given
        val memberId = UUID.randomUUID()
        val clientLoggingInfoEventDto = getClientLoggingInfoEventDto(memberId)
        logClientApiRepository.save(clientLoggingInfoEventDto)
        em.flush()
        em.clear()

        // when
        val canReCreate = authUserInfoRepository.canReCreateRefreshToken(memberId)

        // then
        assertThat(canReCreate).isTrue()
    }

    @Test
    fun `리프레시 토큰 재발급 여부 - false`() {
        // given
        val existEntity = getExpiredTokenEntity()

        // when
        val canReCreate = authUserInfoRepository.canReCreateRefreshToken(existEntity.memberId)

        // then
        assertThat(canReCreate).isFalse()
    }
}