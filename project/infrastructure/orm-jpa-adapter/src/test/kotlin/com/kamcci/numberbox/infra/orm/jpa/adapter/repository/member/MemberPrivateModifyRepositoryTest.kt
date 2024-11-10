package com.kamcci.numberbox.infra.orm.jpa.adapter.repository.member

import com.kamcci.numberbox.app.domain.dto.member.MemberPhoneUpdtDto
import com.kamcci.numberbox.app.port.orm.member.MemberPrivateModifyOrmPort
import com.kamcci.numberbox.infra.orm.jpa.adapter.annotation.TcDBJpaTest
import com.kamcci.numberbox.infra.orm.jpa.adapter.entity.member.MemberPrivateEntity
import jakarta.persistence.EntityManager
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import java.util.*

@TcDBJpaTest
class MemberPrivateModifyRepositoryTest(
    @Autowired
    private val em: EntityManager,
    @Autowired
    private val memberPrivateModifyOrmPort: MemberPrivateModifyOrmPort
) {
    @Test
    fun `휴대폰 번호 변경`() {
        // given
        val memberId = UUID.fromString("33CA3122-CDA8-EA4D-9BC7-037CB86FDB20")
        val phoneUpdtDto = MemberPhoneUpdtDto(memberId, "01098769876")

        // when
        memberPrivateModifyOrmPort.updatePhoneNumber(phoneUpdtDto)
        em.flush()
        em.clear()

        // then
        val memberPrivateEntity = em.find(MemberPrivateEntity::class.java, memberId)
        assertThat(memberPrivateEntity.phoneNumber).isEqualTo(phoneUpdtDto.phoneNumber)
    }
}