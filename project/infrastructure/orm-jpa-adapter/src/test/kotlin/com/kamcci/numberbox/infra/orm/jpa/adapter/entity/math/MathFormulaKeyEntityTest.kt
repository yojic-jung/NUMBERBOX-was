package com.kamcci.numberbox.infra.orm.jpa.adapter.entity.math

import com.kamcci.numberbox.app.domain.enumeration.math.FormulaClassificationType
import com.kamcci.numberbox.app.service.constant.MockTestConstant.SUCCESS_ID
import com.kamcci.numberbox.infra.orm.jpa.adapter.annotation.TcDBJpaTest
import jakarta.persistence.EntityManager
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

@TcDBJpaTest
class MathFormulaKeyEntityTest(
    @Autowired
    private val em: EntityManager
) {
    @Test
    fun `수학 기호 조회`() {
        // given
        val id = SUCCESS_ID

        // when
        val mathFormulaKeyEntity = em.find(MathFormulaKeyEntity::class.java, id)

        // then
        assertThat(mathFormulaKeyEntity.id).isEqualTo(id)
        assertThat(mathFormulaKeyEntity.formulOrder).isOne()
        assertThat(mathFormulaKeyEntity.formulName).isEqualTo("파이")
        assertThat(mathFormulaKeyEntity.formulUi).isEqualTo("&#960;")
        assertThat(mathFormulaKeyEntity.shortcutKey).isEqualTo("1")
        assertThat(mathFormulaKeyEntity.latexGrammer).isEqualTo("\\pi")
        assertThat(mathFormulaKeyEntity.nbGrammer).isEqualTo("&#960;")
        assertThat(mathFormulaKeyEntity.guide).isEqualTo("")
        assertThat(mathFormulaKeyEntity.shortcutKeycode).isEqualTo("49")
        assertThat(mathFormulaKeyEntity.texGrammer).isEqualTo("PI")
        assertThat(mathFormulaKeyEntity.lineChange).isZero()
        assertThat(mathFormulaKeyEntity.classification).isEqualTo(FormulaClassificationType.Main)
    }
}