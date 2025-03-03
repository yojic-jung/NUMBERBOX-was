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
    companion object {
        private const val UNIT_ID = 21001
        private const val TYPE_ID = 1
    }

    @Test
    fun `수학문제 유형 카테고리 조회`() {
        // given
        val id = MathTypeDomain(UNIT_ID, TYPE_ID)

        // when
        val mathCategoryTypeEntity = em.find(MathCategoryTypeEntity::class.java, id)

        // then
        assertEntityProperty(mathCategoryTypeEntity, id)
    }

    private fun assertEntityProperty(mathCategoryTypeEntity: MathCategoryTypeEntity, id: MathTypeDomain) {
        assertThat(mathCategoryTypeEntity.mathTypeDomain).isEqualTo(id)
        assertThat(mathCategoryTypeEntity.mathTypeDomain?.unitId).isEqualTo(UNIT_ID)
        assertThat(mathCategoryTypeEntity.mathTypeDomain?.typeId).isEqualTo(TYPE_ID)
        assertThat(mathCategoryTypeEntity.quesType).isNotNull
        assertThat(mathCategoryTypeEntity.typeOrder).isOne()
    }
}