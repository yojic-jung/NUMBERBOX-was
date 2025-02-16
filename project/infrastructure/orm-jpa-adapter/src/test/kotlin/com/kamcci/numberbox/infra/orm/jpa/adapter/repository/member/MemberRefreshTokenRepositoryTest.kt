package com.kamcci.numberbox.infra.orm.jpa.adapter.repository.member

import com.kamcci.numberbox.infra.orm.jpa.adapter.annotation.TcDBJpaTest
import com.kamcci.numberbox.infra.orm.jpa.adapter.constant.MockOrmConstant.EXIST_MEMBER_ID
import com.kamcci.numberbox.infra.orm.jpa.adapter.constant.MockOrmConstant.EXIST_TOKEN
import com.kamcci.numberbox.infra.orm.jpa.adapter.entity.member.MemberRefreshTokenEntity
import jakarta.persistence.EntityManager
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

@TcDBJpaTest
class MemberRefreshTokenRepositoryTest @Autowired constructor(
    private val memberRefreshTokenRepository: MemberRefreshTokenRepository,
    private val em: EntityManager
) {
    @Test
    fun `MemberRefreshTokenEntity 생성 - 성공`() {
        val memberRefreshTokenEntity = MemberRefreshTokenEntity().apply {
            token = "asfdad"
            memberId = EXIST_MEMBER_ID
        }

        // when
        memberRefreshTokenRepository.save(memberRefreshTokenEntity)
        em.flush()
        em.clear()

        // then
        assertThat(memberRefreshTokenEntity.id).isGreaterThan(0)
    }

    @Test
    fun `토큰 삭제 - 성공`() {
        // when
        val executeCnt = memberRefreshTokenRepository.deleteByToken(EXIST_TOKEN)

        // then
        assertThat(executeCnt).isOne()
    }
}