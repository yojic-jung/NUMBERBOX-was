package com.kamcci.numberbox.infra.orm.adapter.member

import com.kamcci.numberbox.app.domain.dto.member.MemberPrivateSignUpDto
import com.kamcci.numberbox.infra.orm.annotation.TcDBJpaTest
import com.kamcci.numberbox.infra.orm.entity.member.MemberPrivateEntity
import jakarta.persistence.EntityManager
import org.assertj.core.api.Assertions.assertThat
import org.hibernate.exception.ConstraintViolationException
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import java.util.*

@TcDBJpaTest
class MemberPrivateSaveOrmAdapterTest(
    @Autowired
    private val entityManager: EntityManager,
    @Autowired
    private val memberPrivateSaveRepo: MemberPrivateSaveOrmAdapter
) {

    private lateinit var privateSignUpDto: MemberPrivateSignUpDto

    @BeforeEach
    fun `초기화`() {
        privateSignUpDto = MemberPrivateSignUpDto(userName = "홍길동", phoneNumber = "01023456789", birth = "050123")
    }

    @Test
    fun `개인정보 영속화 테스트 - 성공`() {
        // given
        val memberId = UUID.fromString("10ed5466-cda8-ea4d-9bc7-037cb86fdb20")

        // when
        val savedMemberId = memberPrivateSaveRepo.save(memberId, privateSignUpDto)
        entityManager.flush()
        entityManager.clear()

        // then
        val memberPrivateEntity = entityManager.find(MemberPrivateEntity::class.java, savedMemberId)
        assertThat(memberPrivateEntity).isNotNull
        assertThat(memberPrivateEntity.userName).isEqualTo(privateSignUpDto.userName)
        assertThat(memberPrivateEntity.phoneNumber).isEqualTo(privateSignUpDto.phoneNumber)
        assertThat(memberPrivateEntity.birth).isEqualTo(privateSignUpDto.birth)
    }

    @Test
    fun `개인정보 영속화 member 없는 경우 - 실패`() {
        // given
        val memberId = UUID.fromString("29ed5466-cda8-ea4d-9bc7-037cb86fdb20")

        // when
        assertThrows<ConstraintViolationException> {
            memberPrivateSaveRepo.save(memberId, privateSignUpDto)
            entityManager.flush()
        }
    }
}