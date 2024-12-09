package com.kamcci.numberbox.infra.orm.jpa.adapter.entity.member

import com.kamcci.numberbox.infra.orm.jpa.adapter.annotation.TcDBJpaTest
import jakarta.persistence.EntityManager
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

@TcDBJpaTest
class MemberRefreshTokenEntityTest(
    @Autowired
    private val em: EntityManager
) {
    @Test
    fun `MemberRefreshTokenEntity 조회 테스트`() {
        // given
        val id = 1L

        // when
        val refreshTokenEntity = em.find(MemberRefreshTokenEntity::class.java, id)

        // then
        assertThat(refreshTokenEntity.id).isEqualTo(id)
    }
}