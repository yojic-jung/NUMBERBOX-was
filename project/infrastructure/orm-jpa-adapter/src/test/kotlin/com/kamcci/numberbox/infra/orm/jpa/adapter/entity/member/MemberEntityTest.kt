package com.kamcci.numberbox.infra.orm.jpa.adapter.entity.member

import com.kamcci.numberbox.infra.orm.jpa.adapter.annotation.TcDBJpaTest
import jakarta.persistence.EntityManager
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import java.util.*

@TcDBJpaTest
class MemberEntityTest(
    @Autowired
    private val em: EntityManager
) {

    @Test
    fun `memberEntity 조회`() {
        // given
        val memberId = UUID.fromString("10ed5466-cda8-ea4d-9bc7-037cb86fdb20")

        // when
        val memberEntity = em.find(MemberEntity::class.java, memberId)

        // then
        Assertions.assertThat(memberEntity.email).isEqualTo("dywlr@test.com")
        Assertions.assertThat(memberEntity.password).isNotNull()
        Assertions.assertThat(memberEntity.humanStatus).isZero()
        Assertions.assertThat(memberEntity.failCount).isZero()
        Assertions.assertThat(memberEntity.lastFailTime).isNotNull()
        Assertions.assertThat(memberEntity.isTmpPassword).isFalse()
        Assertions.assertThat(memberEntity.lastLoginTime).isNotNull()
        Assertions.assertThat(memberEntity.sysUpdateTime).isNotNull()
        Assertions.assertThat(memberEntity.sysCreateTime).isNotNull()
    }

    @Test
    fun `memberEntity 연관관계 설정`() {
        // given
        val memberId = UUID.fromString("10CA3122-CDA8-EA4D-9BC7-037CB86FDB20")

        // when
        val memberEntity = em.find(MemberEntity::class.java, memberId)

        // then
        Assertions.assertThat(memberEntity.role.get(0).id).isEqualTo(1L)
    }
}