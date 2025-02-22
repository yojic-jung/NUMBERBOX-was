package com.kamcci.numberbox.infra.orm.jpa.adapter.converter.math

import com.kamcci.numberbox.app.domain.enumeration.math.FormulaClassificationType
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class FormulaClassificationTypeConverterTest {
    private val formulaClassificationTypeConverter = FormulaClassificationTypeConverter()

    @Test
    fun `dbData to FormulaClassificationType`() {
        // when
        FormulaClassificationType.entries.forEach { formulaClassificationType ->
            val type = formulaClassificationTypeConverter.convertToEntityAttribute(formulaClassificationType.dbData)

            // then
            assertThat(type).isEqualTo(formulaClassificationType)
        }
    }

    @Test
    fun `null to null`() {
        // when
        val type = formulaClassificationTypeConverter.convertToEntityAttribute(null)

        // then
        assertThat(type).isEqualTo(null)
    }

    @Test
    fun `FormulaClassificationType to dbData`() {
        // given
        val type = FormulaClassificationType.Main

        // when
        val column = formulaClassificationTypeConverter.convertToDatabaseColumn(type)

        // then
        assertThat(column).isEqualTo(type.dbData)
    }
}