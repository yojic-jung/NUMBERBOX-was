package com.kamcci.numberbox.infra.orm.jpa.adapter.converter.math

import com.kamcci.numberbox.app.domain.enumeration.math.MathTypeClassifyType
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class MathTypeClassifyTypeConverterTest {
    private val mathTypeClassifyTypeConverter = MathTypeClassifyTypeConverter()

    @Test
    fun `dbData to MathTypeClassifyType`() {
        // when
        MathTypeClassifyType.entries.forEach { mathTypeClassifyType ->
            val type = mathTypeClassifyTypeConverter.convertToEntityAttribute(mathTypeClassifyType.dbData)

            // then
            assertThat(type).isEqualTo(mathTypeClassifyType)
        }
    }

    @Test
    fun `null(db) to null(attribute)`() {
        // when
        val type = mathTypeClassifyTypeConverter.convertToEntityAttribute(null)

        // then
        assertThat(type).isEqualTo(null)
    }

    @Test
    fun `MathTypeClassifyType to dbData`() {
        // given
        val attrType = MathTypeClassifyType.Simple

        // when
        val dbData = mathTypeClassifyTypeConverter.convertToDatabaseColumn(attrType)

        // then
        assertThat(dbData).isEqualTo(attrType.dbData)
    }

    @Test
    fun `null(attribute) to null(db)`() {
        // when
        val dbData = mathTypeClassifyTypeConverter.convertToDatabaseColumn(null)

        // then
        assertThat(dbData).isEqualTo(null)
    }
}