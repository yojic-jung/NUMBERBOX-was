package com.kamcci.numberbox.infra.persistence.adapter.repository.member

import com.kamcci.numberbox.infra.orm.jpa.adapter.mock.MockMemberWriteRepository
import com.kamcci.numberbox.infra.redis.adapter.hash.member.MemberRedisHash
import com.kamcci.numberbox.infra.redis.mock.MockMemberRedisRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.time.LocalDateTime
import java.util.UUID

class MemberWritePersistenceRepositoryTest {

    lateinit var memberWriteRepository: MockMemberWriteRepository
    lateinit var memberRedisRepository: MockMemberRedisRepository
    lateinit var memberWritePersistenceRepository: MemberWritePersistenceRepository

    @BeforeEach
    fun init() {
        memberWriteRepository = MockMemberWriteRepository()
        memberRedisRepository= MockMemberRedisRepository()
        memberWritePersistenceRepository = MemberWritePersistenceRepository(memberWriteRepository, memberRedisRepository)
    }

    @Test
    fun `(무의미) 회원등록 - 성공`() {
        // given
        val anyEmail = "email"
        val anyPassword = "password"

        // when
        memberWritePersistenceRepository.save(anyEmail, anyPassword)
    }

    @Test
    fun `회원탈퇴 (rdb, redis 모두 삭제) - 성공`() {
        // given
        val anyMemberId= UUID.randomUUID()

        // when
        memberWritePersistenceRepository.drop(anyMemberId)

        // then
        assertThat(memberWriteRepository.executeCnt).isOne()
        assertThat(memberRedisRepository.executeCnt).isOne()
    }

    @Test
    fun `단일 사용자 비밀번호 변경(redis 삭제, rdb 수정) - 성공`() { 
        // given
        val anyMemberId= UUID.randomUUID()
        val anyPassword = "any"

        // when
        memberWritePersistenceRepository.updatePassword(anyMemberId, anyPassword)

        // then
        assertThat(memberWriteRepository.executeCnt).isOne()
        assertThat(memberRedisRepository.executeCnt).isOne()
    }

    @Test
    fun `멀티 사용자 비밀번호 변경(redis 삭제, rdb 수정) - 성공`() {
        // given
        val anyMemberIds= listOf(UUID.randomUUID())
        val anyPassword = "any"

        // when
        memberWritePersistenceRepository.updatePassword(anyMemberIds, anyPassword)

        // then
        assertThat(memberWriteRepository.executeCnt).isOne()
        assertThat(memberRedisRepository.executeCnt).isOne()
    }

    @Test
    fun `이메일로 비밀번호 변경(유저 미존재) - 성공`() {
        // given
        val anyEmail= "any"
        val anyPassword = "any"

        // when
        memberWritePersistenceRepository.updatePassword(anyEmail, anyPassword)

        // then
        assertThat(memberWriteRepository.executeCnt).isOne()
        assertThat(memberRedisRepository.executeCnt).isOne()
    }

    @Test
    fun `이메일로 비밀번호 변경(유저 존재) - 성공`() {
        // given
        val anyMemberId = UUID.randomUUID()
        val anyEmail= "any"
        val anyPassword = "any"
        val memberRedisHash = MemberRedisHash(anyMemberId, anyEmail, anyPassword, listOf())
        val dataStore = memberRedisRepository.dataStore
        dataStore.put(anyMemberId, memberRedisHash)

        // when
        memberWritePersistenceRepository.updatePassword(anyEmail, anyPassword)

        // then
        assertThat(memberWriteRepository.executeCnt).isOne()
        assertThat(memberRedisRepository.executeCnt).isEqualTo(2)
    }

    @Test
    fun `로그인 실패 카운트 변경 - 성공`() {
        // given
        val anyMemberId= UUID.randomUUID()
        val failCount = 1

        // when
        memberWritePersistenceRepository.updateFailCountById(anyMemberId, failCount)

        // then
        assertThat(memberWriteRepository.executeCnt).isOne()
    }

    @Test
    fun `로그인 실패 시간 변경 - 성공`() {
        // given
        val anyMemberId= UUID.randomUUID()
        val lastFailTime = LocalDateTime.now()

        // when
        memberWritePersistenceRepository.updateLastFailTimeById(anyMemberId, lastFailTime)

        // then
        assertThat(memberWriteRepository.executeCnt).isOne()

    }
}