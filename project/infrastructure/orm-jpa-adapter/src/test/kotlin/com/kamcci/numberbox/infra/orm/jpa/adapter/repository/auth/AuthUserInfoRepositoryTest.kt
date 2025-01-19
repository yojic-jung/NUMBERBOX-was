package com.kamcci.numberbox.infra.orm.jpa.adapter.repository.auth

import com.kamcci.numberbox.infra.orm.jpa.adapter.annotation.TcDBJpaTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import java.util.*

@TcDBJpaTest
class AuthUserInfoRepositoryTest(
    @Autowired
    private val authUserInfoRepository: AuthUserInfoRepository
) {
    @Test
    fun `멤버엔티티 조회 - 존재`() {
        // given
        val email = "dywlr@test.com"

        // when
        val authUser = authUserInfoRepository.loadUserByUsername(email)

        // then
        assertThat(authUser?.username).isEqualTo(email)
    }

    @Test
    fun `멤버엔티티 조회 - 미존재`() {
        // given
        val email = "dywㄴㅇㅁㄹlr@test.com"

        // when
        val authUser = authUserInfoRepository.loadUserByUsername(email)

        // then
        assertThat(authUser).isNull()
    }

    @Test
    fun `멤버 엔티티 토큰으로 조회 - 성공`() {
        // given
        val token =
            "eyJhbGciOiJIUzI1NiJ9.eyJuc29vaGFrLmNvbSI6dHJ1ZSwiaXNzIjoibnNvb2hhayIsInN1YiI6Im5zb29oYWtSZWZyZXNoVG9rZW4iLCJhdWQiOiJ1c2VyIiwiZXhwIjoxNzAxMTU1NjI3LCJpYXQiOjE2OTg1NjM2Mjd9.IRiJaK2jH-3DskfW4N2Rhm9eVzhB9Mswp8-JlfDN-Ws"

        // when
        val uuid = authUserInfoRepository.loadUserIdByRefreshToken(token)

        // then
        assertThat(uuid.toString()).isEqualTo("10ed5466-cda8-ea4d-9bc7-037cb86fdb20")
    }

    @Test
    fun `리프레시 토큰 재발급 여부 - true`() {
        // given
        val memberId = UUID.fromString("10ed5466-cda8-ea4d-9bc7-037cb86fdb20")

        // when
        val canReCreate = authUserInfoRepository.canReCreateRefreshToken(memberId)

        // then
        assertThat(canReCreate).isTrue()
    }

    @Test
    fun `리프레시 토큰 재발급 여부 - false`() {
        // given
        val memberId = UUID.fromString("12ed5466-cda8-ea4d-9bc7-037cb86fdb20")

        // when
        val canReCreate = authUserInfoRepository.canReCreateRefreshToken(memberId)

        // then
        assertThat(canReCreate).isFalse()
    }
}