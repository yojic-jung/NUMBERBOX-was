package com.kamcci.numberbox.infra.orm.jpa.adapter.entity.math

import com.kamcci.numberbox.infra.orm.jpa.adapter.annotation.TcDBJpaTest
import jakarta.persistence.EntityManager
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

@TcDBJpaTest
class MathCategoryUnitEntityTest(
    @Autowired
    private val em: EntityManager
) {

    @Test
    fun `수학문제 단원 카테고리 조회`() {
        // given
        val unitId = 21001

        // when
        val mathCategoryUnitEntity = em.find(MathCategoryUnitEntity::class.java, unitId)

        // then
        assertThat(mathCategoryUnitEntity.id).isEqualTo(unitId)
        assertThat(mathCategoryUnitEntity.subject).isEqualTo("중등 1-1")
        assertThat(mathCategoryUnitEntity.firUnit).isEqualTo("수와 연산")
        assertThat(mathCategoryUnitEntity.secUnit).isEqualTo("소인수분해")
        assertThat(mathCategoryUnitEntity.thrUnit).isEqualTo("소인수분해")
    }
}