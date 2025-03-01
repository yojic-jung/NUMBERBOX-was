package com.kamcci.numberbox.infra.orm.jpa.adapter.repository.auth

import com.kamcci.modules.logging.control.dto.ClientLoggingInfoEventDto
import com.kamcci.modules.logging.control.dto.HttpRequestLoggingDto
import com.kamcci.modules.logging.control.dto.HttpResponseLoggingDto
import com.kamcci.numberbox.infra.orm.jpa.adapter.annotation.TcDBJpaTest
import com.kamcci.numberbox.infra.orm.jpa.adapter.dummy.member.MemberDummyFactory.NOT_EXIST_MEMBER_EMAIL
import com.kamcci.numberbox.infra.orm.jpa.adapter.dummy.member.MemberDummyFactory.getMemberDummyEntity
import com.kamcci.numberbox.infra.orm.jpa.adapter.dummy.member.MemberRefreshTokenDummyFactory.getExpiredTokenDummyEntity
import com.kamcci.numberbox.infra.orm.jpa.adapter.repository.log.LogClientApiRepository
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
    // 테스트 데이터
    private val memberDummyEntity = getMemberDummyEntity()
    private val expiredTokenDummyEntity = getExpiredTokenDummyEntity()

    @Test
    fun `멤버엔티티 조회 - 존재`() {
        // given
        val email = memberDummyEntity.email

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
        val token = expiredTokenDummyEntity.token

        // when
        val memberId = authUserInfoRepository.loadUserIdByRefreshToken(token)

        // then
        assertThat(memberId).isEqualTo(expiredTokenDummyEntity.memberId)
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

    private fun getClientLoggingInfoEventDto(memberId: UUID, httpStatus: Int = 200) =
        ClientLoggingInfoEventDto(
            HttpRequestLoggingDto(
                memberId,
                "Chrome",
                "Mac",
                "127.0.0.1",
                "GET",
                "/sdfa/adf",
                "sadf"
            ), HttpResponseLoggingDto(httpStatus)
        )

    @Test
    fun `리프레시 토큰 재발급 여부 - false`() {
        // when
        val canReCreate = authUserInfoRepository.canReCreateRefreshToken(expiredTokenDummyEntity.memberId)

        // then
        assertThat(canReCreate).isFalse()
    }
}