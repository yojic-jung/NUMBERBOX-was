package com.kamcci.numberbox.infra.orm.jpa.adapter.converter.math

import com.kamcci.numberbox.app.domain.enumeration.math.MathTypeClassifyType
import com.kamcci.numberbox.infra.orm.jpa.adapter.util.CustomAssertUtil.assertDataToEnumMapping
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class MathTypeClassifyTypeConverterTest {
    private val mathTypeClassifyTypeConverter = MathTypeClassifyTypeConverter()

    @Test
    fun `dbData to MathTypeClassifyType`() {
        assertDataToEnumMapping(MathTypeClassifyType::class.java) { enum ->
            mathTypeClassifyTypeConverter.convertToEntityAttribute(enum?.dbData)
        }
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