package com.kamcci.numberbox.infra.orm.jpa.adapter.repository.member

import com.kamcci.numberbox.infra.orm.jpa.adapter.annotation.TcDBJpaTest
import com.kamcci.numberbox.infra.orm.jpa.adapter.dummy.member.MemberDummyFactory.getMemberDummyEntity
import com.kamcci.numberbox.infra.orm.jpa.adapter.dummy.member.MemberRefreshTokenDummyFactory.getExpiredTokenDummyEntity
import com.kamcci.numberbox.infra.orm.jpa.adapter.entity.member.MemberRefreshTokenEntity
import jakarta.persistence.EntityManager
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

@TcDBJpaTest
class MemberRefreshTokenRedisRepositoryTest @Autowired constructor(
    private val memberRefreshTokenRepository: MemberRefreshTokenRepository,
    private val em: EntityManager
) {
    @Test
    fun `MemberRefreshTokenEntity 생성 - 성공`() {
        val memberRefreshTokenEntity = MemberRefreshTokenEntity().apply {
            token = "any"
            memberId = getMemberDummyEntity().memberId
        }

        // when
        memberRefreshTokenRepository.save(memberRefreshTokenEntity)
        em.flush()
        em.clear()

        // then
        assertThat(memberRefreshTokenEntity.id).isPositive()
    }

    @Test
    fun `토큰 삭제 - 성공`() {
        // given
        val token = getExpiredTokenDummyEntity().token

        // when
        val executeCnt = memberRefreshTokenRepository.deleteByToken(token)

        // then
        assertThat(executeCnt).isOne()
    }
}