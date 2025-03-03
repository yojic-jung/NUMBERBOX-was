package com.kamcci.numberbox.infra.orm.jpa.adapter.repository.member

import com.kamcci.numberbox.infra.orm.jpa.adapter.annotation.TcDBJpaTest
import com.kamcci.numberbox.infra.orm.jpa.adapter.dummy.member.MemberDummyFactory.NOT_EXIST_MEMBER_EMAIL
import com.kamcci.numberbox.infra.orm.jpa.adapter.dummy.member.MemberDummyFactory.NOT_EXIST_MEMBER_ID
import com.kamcci.numberbox.infra.orm.jpa.adapter.dummy.member.MemberDummyFactory.getDisabledMemberId
import com.kamcci.numberbox.infra.orm.jpa.adapter.dummy.member.MemberDummyFactory.getMemberDummyEntity
import com.kamcci.numberbox.infra.orm.jpa.adapter.dummy.member.MemberDummyFactory.getTmpPwMemberId
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

@TcDBJpaTest
class MemberReadRepositoryTest(
    @Autowired
    private val memberReadRepo: MemberReadRepository
) {
    private val memberDummyEntity = getMemberDummyEntity()

    @Test
    fun `존재하는 이메일로 id 조회 - 성공`() {
        // given
        val existEmail = memberDummyEntity.email

        // when
        val memberId = memberReadRepo.readIdByEmail(existEmail)

        // then
        assertThat(memberId).isEqualTo(memberDummyEntity.memberId)
    }

    @Test
    fun `존재하지 않는 이메일로 id 조회 - 성공`() {
        // given & when
        val memberId = memberReadRepo.readIdByEmail(NOT_EXIST_MEMBER_EMAIL)

        // then
        assertThat(memberId).isNull()
    }

    @Test
    fun `이름과 휴대폰번호로 이메일 조회 - 성공`() {
        // given
        val username = memberDummyEntity.userName!!
        val phone = memberDummyEntity.phone!!

        // when
        val email = memberReadRepo.readEmailByUsernameAndPhone(username, phone)

        // then
        assertThat(email).isEqualTo(memberDummyEntity.email)
    }

    @Test
    fun `이메일 조회 - 존재`() {
        // given
        val email = memberDummyEntity.email

        // when
        val isExist = memberReadRepo.existEmail(email)

        // then
        assertThat(isExist).isTrue()
    }

    @Test
    fun `이메일 조회 - 미존재`() {
        // given & when
        val isExist = memberReadRepo.existEmail(NOT_EXIST_MEMBER_EMAIL)

        // then
        assertThat(isExist).isFalse()
    }

    @Test
    fun `계정 존재여부 확인`() {
        // given
        val email = memberDummyEntity.email

        // when
        val isExist = memberReadRepo.existsByEmail(email)

        // then
        assertThat(isExist).isTrue()
    }

    @Test
    fun `암호화 비밀번호 조회`() {
        // given
        val memberId = memberDummyEntity.memberId

        // when
        val password = memberReadRepo.readPasswordByMemberId(memberId)

        // then
        assertThat(password).isNotNull
    }

    @Test
    fun `존재하는 id로 failCount 조회 - 성공`() {
        // given
        val memberId = NOT_EXIST_MEMBER_ID

        // when
        val failCount = memberReadRepo.readFailCountById(memberId)

        // then
        assertThat(failCount).isNull()

    }

    @Test
    fun `존재하지 않는 id로 failCount 조회 - 성공`() {
        // given
        val memberId = NOT_EXIST_MEMBER_ID

        // when
        val failCount = memberReadRepo.readFailCountById(memberId)

        // then
        assertThat(failCount).isNull()

    }

    @Test
    fun `존재하는 id로 lastFailTime 조회 - 성공`() {
        // given
        val memberId = NOT_EXIST_MEMBER_ID

        // when
        val lastFailTime = memberReadRepo.readLastFailTimeById(memberId)

        // then
        assertThat(lastFailTime).isNull()
    }

    @Test
    fun `존재하지 않는 id로 lastFailTime 조회 - 성공`() {
        // given
        val memberId = NOT_EXIST_MEMBER_ID

        // when
        val lastFailTime = memberReadRepo.readLastFailTimeById(memberId)

        // then
        assertThat(lastFailTime).isNull()
    }

    @Test
    fun `존재하는 이메일로 계정 존재여부 확인 - 성공`() {
        // given
        val email = memberDummyEntity.email

        // when
        val isExist = memberReadRepo.existsByEmail(email)

        // then
        assertThat(isExist).isTrue()
    }

    @Test
    fun `존재하지 않는 이메일로 계정 존재여부 확인 - 성공`() {
        // given & when
        val isExist = memberReadRepo.existsByEmail(NOT_EXIST_MEMBER_EMAIL)

        // then
        assertThat(isExist).isFalse()
    }

    @Test
    fun `임시 비밀번호 발급자 모두 조회`() {
        // given
        val isTmpPwCond = true

        // when
        val memberIdList = memberReadRepo.readByIsTmpPassword(isTmpPwCond, 10)

        // then
        val tmpPwMemberId = getTmpPwMemberId()
        assertThat(memberIdList).contains(tmpPwMemberId)
    }

    @Test
    fun `활성 비활성 조건으로 계정 조회`() {
        // given
        val disableStatus = 1

        // when
        val memberIdList = memberReadRepo.readUserIdByHumanStatus(disableStatus)

        // then
        val disableMemberId = getDisabledMemberId()
        assertThat(memberIdList).contains(disableMemberId)
    }
}