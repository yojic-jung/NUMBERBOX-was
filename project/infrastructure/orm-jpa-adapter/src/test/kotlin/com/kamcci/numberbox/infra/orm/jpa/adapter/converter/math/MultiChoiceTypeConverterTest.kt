package com.kamcci.numberbox.infra.orm.jpa.adapter.converter.math

import com.kamcci.numberbox.app.domain.enumeration.math.MultiChoiceType
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

class MultiChoiceTypeConverterTest {
    private val multiChoiceTypeConverter = MultiChoiceTypeConverter()

    @Test
    fun `to MultiChoiceType`() {
        // when
        MultiChoiceType.entries.forEach { multiChoiceType ->
            val type = multiChoiceTypeConverter.convertToEntityAttribute(multiChoiceType.dbData)

            // then
            Assertions.assertThat(type).isEqualTo(multiChoiceType)
        }
    }

    @Test
    fun `null to null`() {
        // when
        val type = multiChoiceTypeConverter.convertToEntityAttribute(null)

        // then
        Assertions.assertThat(type).isEqualTo(null)
    }
}