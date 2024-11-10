package com.kamcci.numberbox.infra.orm.jpa.adapter.repository.member

import com.kamcci.numberbox.infra.orm.jpa.adapter.annotation.TcDBJpaTest
import com.kamcci.numberbox.infra.orm.jpa.adapter.entity.member.MemberEntity
import jakarta.persistence.EntityManager
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit
import java.util.*

@TcDBJpaTest
class MemberModifyRepositoryTest(
    @Autowired
    private val em: EntityManager,
    @Autowired
    private val memberModifyRepository: MemberModifyRepository
) {
    @Test
    fun `멤버 id로 비밀번호 변경 - 성공`() {
        // given
        val memberId = UUID.fromString("10ed5466-cda8-ea4d-9bc7-037cb86fdb20")
        val password = "tmp"

        // when
        memberModifyRepository.updatePassword(memberId, password)

        // then
        val memberEntity = em.find(MemberEntity::class.java, memberId)
        assertThat(memberEntity.password).isEqualTo(password)
    }

    @Test
    fun `이메일로 비밀번호 변경 - 성공`() {
        // given
        val email = "dywlr@test.com"
        val password = "tmp"

        // when
        memberModifyRepository.updatePassword(email, password)

        // then
        val memberEntity = em.find(MemberEntity::class.java, UUID.fromString("10ed5466-cda8-ea4d-9bc7-037cb86fdb20"))
        assertThat(memberEntity.password).isEqualTo(password)
    }


    @Test
    fun `로그인 실패 카운트 변경 - 성공`() {
        // given
        val memberId = UUID.fromString("10ed5466-cda8-ea4d-9bc7-037cb86fdb20")
        val failCount = 0

        // when
        memberModifyRepository.updateFailCountById(memberId, failCount)

        // then
        val memberEntity = em.find(MemberEntity::class.java, memberId)
        assertThat(memberEntity.failCount).isEqualTo(failCount)
    }

    @Test
    fun `마지막 실패 시간 변경 - 성공`() {
        // given
        val memberId = UUID.fromString("10ed5466-cda8-ea4d-9bc7-037cb86fdb20")
        val failTime = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS)

        // when
        memberModifyRepository.updateLastFailTimeById(memberId, failTime)
        em.flush()
        em.clear()

        // then (DB 접근 시간 고려하여 1초 정도의 오차 범위 허용)
        val memberEntity = em.find(MemberEntity::class.java, memberId)
        println(failTime)
        println(memberEntity.lastFailTime)
        assertThat(failTime).isEqualTo(memberEntity.lastFailTime)
    }
}