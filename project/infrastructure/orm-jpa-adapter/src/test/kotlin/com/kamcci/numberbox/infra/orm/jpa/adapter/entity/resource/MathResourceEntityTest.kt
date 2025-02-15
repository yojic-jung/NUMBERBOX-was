package com.kamcci.numberbox.infra.orm.jpa.adapter.entity.resource

import com.kamcci.numberbox.infra.orm.jpa.adapter.annotation.TcDBJpaTest
import jakarta.persistence.EntityManager
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

@TcDBJpaTest
class MathResourceEntityTest(
    @Autowired
    private val em: EntityManager
) {
    @Test
    fun `MathResourceEntity 연관관계 테스트`() {
        // given
        val id = 1L

        // when
        val mathResourceEntity = em.find(MathResourceEntity::class.java, id)

        // then
        assertThat(mathResourceEntity.mathResourceCate.get(0).id).isOne()
        assertThat(mathResourceEntity.mathResourceImg.get(0).id).isOne()
    }
}