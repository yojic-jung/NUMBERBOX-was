package com.kamcci.numberbox.infra.orm.jpa.adapter.entity.resource

import com.kamcci.numberbox.infra.orm.jpa.adapter.annotation.TcDBJpaTest
import jakarta.persistence.EntityManager
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

@TcDBJpaTest
class MathResourceCateEntityTest(
    @Autowired
    private val em: EntityManager
) {
    @Test
    fun `MathResourceCateEntity 조회 테스트`() {
        // given
        val id = 1L

        // when
        val mathResourceCateEntity = em.find(MathResourceCateEntity::class.java, id)

        // then
        Assertions.assertThat(mathResourceCateEntity.id).isEqualTo(id)
    }
}