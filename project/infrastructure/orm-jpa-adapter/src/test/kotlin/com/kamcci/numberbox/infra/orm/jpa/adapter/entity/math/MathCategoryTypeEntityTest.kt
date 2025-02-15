package com.kamcci.numberbox.infra.orm.jpa.adapter.entity.math

import com.kamcci.numberbox.infra.orm.jpa.adapter.annotation.TcDBJpaTest
import jakarta.persistence.EntityManager
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

@TcDBJpaTest
class MathCategoryTypeEntityTest(
    @Autowired
    private val em: EntityManager
) {

    @Test
    fun `수학문제 유형 카테고리 조회`() {
        // given
        val unitId = 21001
        val typeId = 1
        val id = MathTypeDomain(unitId, typeId)

        // when
        val mathCategoryTypeEntity = em.find(MathCategoryTypeEntity::class.java, id)

        // then
        assertThat(mathCategoryTypeEntity.mathTypeDomain).isEqualTo(id)
        assertThat(mathCategoryTypeEntity.mathTypeDomain?.unitId).isEqualTo(unitId)
        assertThat(mathCategoryTypeEntity.mathTypeDomain?.typeId).isEqualTo(typeId)
        assertThat(mathCategoryTypeEntity.quesType).isEqualTo("약수와 배수")
        assertThat(mathCategoryTypeEntity.typeOrder).isOne()
    }
}