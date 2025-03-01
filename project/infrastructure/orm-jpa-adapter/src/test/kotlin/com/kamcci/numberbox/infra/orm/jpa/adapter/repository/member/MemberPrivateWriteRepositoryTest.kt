package com.kamcci.numberbox.infra.orm.jpa.adapter.repository.member

import com.kamcci.numberbox.app.domain.dto.member.MemberPhoneUpdtDto
import com.kamcci.numberbox.app.domain.dto.member.MemberPrivateSignUpDto
import com.kamcci.numberbox.app.port.orm.member.MemberPrivateWriteOrmPort
import com.kamcci.numberbox.infra.orm.jpa.adapter.annotation.TcDBJpaTest
import com.kamcci.numberbox.infra.orm.jpa.adapter.dummy.member.MemberPrivateDummyFactory.getMemberPrivateDummyEntity
import com.kamcci.numberbox.infra.orm.jpa.adapter.dummy.member.MemberPrivateDummyFactory.getMemberPrivateDummyEntity4Del
import com.kamcci.numberbox.infra.orm.jpa.adapter.entity.member.MemberPrivateEntity
import jakarta.persistence.EntityManager
import org.assertj.core.api.Assertions.assertThat
import org.hibernate.exception.ConstraintViolationException
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import java.util.*

@TcDBJpaTest
class MemberPrivateWriteRepositoryTest @Autowired constructor(
    private val em: EntityManager,
    private val memberPrivateWriteOrmPort: MemberPrivateWriteOrmPort
) {
    private val privateSignUpDto =
        MemberPrivateSignUpDto(userName = "홍길동", phoneNumber = "01023456789", birth = "050123")


    @Test
    fun `개인정보 영속화 테스트 - 성공`() {
        // given
        val memberId = UUID.fromString("10ca3122-cda8-ea4d-9bc7-037cb86fdb20")

        // when
        val savedMemberId = memberPrivateWriteOrmPort.save(memberId, privateSignUpDto)
        em.flush()
        em.clear()

        // then
        val memberPrivateEntity = em.find(MemberPrivateEntity::class.java, savedMemberId)
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
            memberPrivateWriteOrmPort.save(memberId, privateSignUpDto)
            em.flush()
        }
    }

    @Test
    fun `휴대폰 번호 변경`() {
        // given
        val memberId = getMemberPrivateDummyEntity().memberId
        val phoneUpdtDto = MemberPhoneUpdtDto(memberId, "01098769876")

        // when
        memberPrivateWriteOrmPort.updatePhoneNumber(phoneUpdtDto)
        em.flush()
        em.clear()

        // then
        val memberPrivateEntity = em.find(MemberPrivateEntity::class.java, memberId)
        assertThat(memberPrivateEntity.phoneNumber).isEqualTo(phoneUpdtDto.phoneNumber)
    }

    @Test
    fun `개인정보 파기`() {
        // given
        val memberId = getMemberPrivateDummyEntity4Del().memberId

        // when
        val executeRowCnt = memberPrivateWriteOrmPort.updatePrivateToNull(memberId)

        // then
        assertThat(executeRowCnt).isOne()
    }
}