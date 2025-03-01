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
        assertThat(mathCategoryUnitEntity.subject).isNotNull
        assertThat(mathCategoryUnitEntity.firUnit).isNotNull
        assertThat(mathCategoryUnitEntity.secUnit).isNotNull
        assertThat(mathCategoryUnitEntity.thrUnit).isNotNull
    }
}