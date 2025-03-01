package com.kamcci.numberbox.infra.orm.jpa.adapter.entity.math

import com.kamcci.numberbox.infra.orm.jpa.adapter.annotation.TcDBJpaTest
import jakarta.persistence.EntityManager
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import java.util.*

@TcDBJpaTest
class MathContentsLikeDomainTest(
    @Autowired
    private val em: EntityManager
) {

    @Test
    fun `수학문제 좋아요 정보 조회`() {
        // given
        val contentsId = 1L
        val memberId = UUID.fromString("10ed5466-cda8-ea4d-9bc7-037cb86fdb20")
        val id = MathContentsLikeDomain(contentsId, memberId)

        // when
        val mathContentsLikeEntity = em.find(MathContentsLikeEntity::class.java, id)

        // then
        Assertions.assertThat(mathContentsLikeEntity.id?.contentsId).isEqualTo(contentsId)
        Assertions.assertThat(mathContentsLikeEntity.id?.memberId).isEqualTo(memberId)
    }
}