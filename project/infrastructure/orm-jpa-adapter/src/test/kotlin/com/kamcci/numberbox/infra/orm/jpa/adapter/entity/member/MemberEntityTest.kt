package com.kamcci.numberbox.infra.orm.jpa.adapter.entity.member

import com.kamcci.numberbox.infra.orm.jpa.adapter.annotation.TcDBJpaTest
import com.kamcci.numberbox.infra.orm.jpa.adapter.dummy.member.MemberDummyFactory.getMemberDummyEntity
import com.kamcci.numberbox.infra.orm.jpa.adapter.dummy.member.MemberRoleDummyFactory.getMemberRoleDummyEntity
import jakarta.persistence.EntityManager
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

@TcDBJpaTest
class MemberEntityTest(
    @Autowired
    private val em: EntityManager
) {
    private val dummyEntity = getMemberDummyEntity()

    @Test
    fun `memberEntity 조회`() {
        // given
        val memberId = dummyEntity.memberId

        // when
        val memberEntity = em.find(MemberEntity::class.java, memberId)

        // then
        Assertions.assertThat(memberEntity.email).isEqualTo(dummyEntity.email)
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
        val memberId = getMemberRoleDummyEntity().memberId

        // when
        val memberEntity = em.find(MemberEntity::class.java, memberId)

        // then
        Assertions.assertThat(memberEntity.role[0].id).isOne()
    }
}