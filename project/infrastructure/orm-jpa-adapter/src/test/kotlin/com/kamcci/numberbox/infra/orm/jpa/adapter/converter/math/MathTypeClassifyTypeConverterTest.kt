package com.kamcci.numberbox.infra.orm.jpa.adapter.converter.math

import com.kamcci.numberbox.app.domain.enumeration.math.MathTypeClassifyType
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class MathTypeClassifyTypeConverterTest {
    private val mathTypeClassifyTypeConverter = MathTypeClassifyTypeConverter()

    @Test
    fun `to MathTypeClassifyType`() {
        // given
        val mathTypeClassifyTypeList: MutableList<Pair<String?, MathTypeClassifyType?>> = mutableListOf()
        MathTypeClassifyType.entries.forEach {
            mathTypeClassifyTypeList.add(Pair(it.id, it))
        }
        mathTypeClassifyTypeList.add(Pair(null, null))

        // when
        for (mathTypeClassifyType in mathTypeClassifyTypeList) {
            val type = mathTypeClassifyTypeConverter.convertToEntityAttribute(mathTypeClassifyType.first)

            // then
            assertThat(type).isEqualTo(mathTypeClassifyType.second)
        }
    }

    @Test
    fun `to dbData - null`() {
        // given
        val dbData = mathTypeClassifyTypeConverter.convertToDatabaseColumn(null)

        // then
        assertThat(dbData).isEqualTo(null)
    }

    @Test
    fun `to dbData - 값 존재`() {
        // given
        val dbData = mathTypeClassifyTypeConverter.convertToDatabaseColumn(MathTypeClassifyType.Simple)

        // then
        assertThat(dbData).isEqualTo(MathTypeClassifyType.Simple.id)
    }
}