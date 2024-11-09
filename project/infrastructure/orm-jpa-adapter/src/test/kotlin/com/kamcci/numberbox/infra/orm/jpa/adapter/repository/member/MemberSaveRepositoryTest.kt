package com.kamcci.numberbox.infra.orm.jpa.adapter.repository.member

import com.kamcci.numberbox.infra.orm.annotation.TcDBJpaTest
import jakarta.persistence.EntityManager
import org.assertj.core.api.Assertions.assertThat
import org.hibernate.exception.ConstraintViolationException
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired

@TcDBJpaTest
class MemberSaveRepositoryTest(
    @Autowired
    private val em: EntityManager,
    @Autowired
    private val memberSaveRepo: MemberSaveRepository
) {
    @Test
    fun `멤버 영속화 성공 - 성공`() {
        // given
        val email = "nonExsit@test123.com"
        val password = "testPW"

        // when
        val memberId = memberSaveRepo.save(email, password)
        em.flush()

        // then
        assertThat(memberId).isNotNull
    }

    @Test
    fun `중복 이메일 멤버 영속화 - 실패`() {
        // given
        val duplicateEmail = "test@test.com"
        val password = "testPW"

        // when
        assertThrows<ConstraintViolationException> {
            memberSaveRepo.save(duplicateEmail, password)
            em.flush()
        }

    }
}