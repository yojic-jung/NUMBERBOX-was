package com.kamcci.numberbox.infra.orm.jpa.adapter.converter.math

import com.kamcci.numberbox.app.domain.enumeration.math.FormulaClassificationType
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class FormulaClassificationTypeConverterTest {
    private val formulaClassificationTypeConverter = FormulaClassificationTypeConverter()

    @Test
    fun `to FormulaClassificationType`() {
        // given
        val formulaClassificationTypeList: MutableList<Pair<String?, FormulaClassificationType?>> = mutableListOf()
        FormulaClassificationType.entries.forEach {
            formulaClassificationTypeList.add(Pair(it.dbData, it))
        }
        formulaClassificationTypeList.add(Pair(null, null))

        // when
        for (formulaClassificationType in formulaClassificationTypeList) {
            val type = formulaClassificationTypeConverter.convertToEntityAttribute(formulaClassificationType.first)

            // then
            assertThat(type).isEqualTo(formulaClassificationType.second)
        }
    }

    @Test
    fun `column to property`() {
        // given
        val converter = FormulaClassificationTypeConverter()

        // when
        val column = converter.convertToDatabaseColumn(FormulaClassificationType.Main)

        // then
        assertThat(column).isEqualTo("main")
    }
}