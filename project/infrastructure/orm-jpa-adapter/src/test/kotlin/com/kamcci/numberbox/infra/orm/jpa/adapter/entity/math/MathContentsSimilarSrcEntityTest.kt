package com.kamcci.numberbox.infra.orm.jpa.adapter.entity.math

import com.kamcci.numberbox.infra.orm.jpa.adapter.annotation.TcDBJpaTest
import jakarta.persistence.EntityManager
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

@TcDBJpaTest
class MathContentsSimilarSrcEntityTest(
    @Autowired
    private val em: EntityManager
) {
    @Test
    fun `수학문제 출처 연관관계 설정`() {
        // given
        val id = 1L

        // when
        val mathContentsSimilarSrcEntity = em.find(MathContentsSimilarSrcEntity::class.java, id)

        // then
        assertThat(mathContentsSimilarSrcEntity.mathContents?.id).isOne()
    }
}
