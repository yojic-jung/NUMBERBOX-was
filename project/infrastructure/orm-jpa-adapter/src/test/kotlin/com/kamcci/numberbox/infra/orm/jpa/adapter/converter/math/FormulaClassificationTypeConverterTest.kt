package com.kamcci.numberbox.infra.orm.jpa.adapter.converter.math

import com.kamcci.numberbox.app.domain.enumeration.math.FormulaClassificationType
import com.kamcci.numberbox.infra.orm.jpa.adapter.util.CustomAssertUtil.assertDataToEnumMapping
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class FormulaClassificationTypeConverterTest {
    private val formulaClassificationTypeConverter = FormulaClassificationTypeConverter()

    @Test
    fun `dbData to FormulaClassificationType`() {
        assertDataToEnumMapping(FormulaClassificationType::class.java) { enum ->
            formulaClassificationTypeConverter.convertToEntityAttribute(enum?.dbData)
        }
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