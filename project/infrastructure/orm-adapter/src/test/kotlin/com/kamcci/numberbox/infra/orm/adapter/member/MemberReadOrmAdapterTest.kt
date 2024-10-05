package com.kamcci.numberbox.infra.orm.adapter.member

import com.kamcci.numberbox.infra.orm.annotation.TcDBJpaTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import java.util.*

@TcDBJpaTest
class MemberReadOrmAdapterTest(
    @Autowired
    private val memberReadRepo: MemberReadOrmAdapter
) {
    companion object {
        const val EXIST_EMAIL = "dywlr@test.com"
        const val NONE_EXIST_EMAIL = "non_exist@test.com"

        const val EXIST_ID = "10ed5466-cda8-ea4d-9bc7-037cb86fdb20"
        const val NONE_EXIST_ID = "11dc3466-cda8-ea4d-9bc7-037cb86fdb20"
    }

    @Test
    fun `존재하는 이메일로 id 조회 - 성공`() {
        // given & when
        val memberId = memberReadRepo.findIdByEmail(EXIST_EMAIL)

        // then
        assertThat(memberId).isNotNull
    }

    @Test
    fun `존재하지 않는 이메일로 id 조회 - 성공`() {
        // given & when
        val memberId = memberReadRepo.findIdByEmail(NONE_EXIST_EMAIL)

        // then
        assertThat(memberId).isNull()
    }

    @Test
    fun `이름과 휴대폰번호로 이메일 조회 - 성공`() {
        // given & when
        val username = "홍길동"
        val phone = "01009870987"
        val email = memberReadRepo.findEmailByUsernameAndPhone(username, phone)

        // then
        assertThat(email).isEqualTo("dywlr@test.com")

    }

    @Test
    fun `존재하는 id로 failCount 조회 - 성공`() {
        // given
        val memberId = UUID.fromString(EXIST_ID)

        // when
        val failCount = memberReadRepo.findFailCountById(memberId)

        // then
        assertThat(failCount).isNotNull

    }

    @Test
    fun `존재하지 않는 id로 failCount 조회 - 성공`() {
        // given
        val memberId = UUID.fromString(NONE_EXIST_ID)

        // when
        val failCount = memberReadRepo.findFailCountById(memberId)

        // then
        assertThat(failCount).isNull()

    }

    @Test
    fun `존재하는 id로 lastFailTime 조회 - 성공`() {
        // given
        val memberId = UUID.fromString(NONE_EXIST_ID)

        // when
        val lastFailTime = memberReadRepo.findLastFailTimeById(memberId)

        // then
        assertThat(lastFailTime).isNull()
    }

    @Test
    fun `존재하지 않는 id로 lastFailTime 조회 - 성공`() {
        // given
        val memberId = UUID.fromString(NONE_EXIST_ID)

        // when
        val lastFailTime = memberReadRepo.findLastFailTimeById(memberId)

        // then
        assertThat(lastFailTime).isNull()
    }

    @Test
    fun `존재하는 이메일로 계정 존재여부 확인 - 성공`() {
        // given & when
        val isExist = memberReadRepo.existsByEmail(EXIST_EMAIL)

        // then
        assertThat(isExist).isTrue()
    }

    @Test
    fun `존재하지 않는 이메일로 계정 존재여부 확인 - 성공`() {
        // given & when
        val isExist = memberReadRepo.existsByEmail(NONE_EXIST_EMAIL)

        // then
        assertThat(isExist).isFalse()
    }
}