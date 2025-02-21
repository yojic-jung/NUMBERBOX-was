package com.kamcci.numberbox.infra.orm.jpa.adapter.entity.math

import com.kamcci.numberbox.infra.orm.jpa.adapter.annotation.TcDBJpaTest
import com.kamcci.numberbox.infra.orm.jpa.adapter.sample.member.MembersSampleData
import jakarta.persistence.EntityManager
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

@TcDBJpaTest
class MathContentsLikeDomainTest(
    @Autowired
    private val em: EntityManager
) {
    @Test
    fun `수학문제 좋아요 정보 조회`() {
        // given
        val contentsId = 1L
        val memberId = MembersSampleData.getMemberId1()
        val id = MathContentsLikeDomain(contentsId, memberId)

        // when
        val mathContentsLikeEntity = em.find(MathContentsLikeEntity::class.java, id)

        // then
        Assertions.assertThat(mathContentsLikeEntity.id?.contentsId).isEqualTo(contentsId)
        Assertions.assertThat(mathContentsLikeEntity.id?.memberId).isEqualTo(memberId)
    }
}