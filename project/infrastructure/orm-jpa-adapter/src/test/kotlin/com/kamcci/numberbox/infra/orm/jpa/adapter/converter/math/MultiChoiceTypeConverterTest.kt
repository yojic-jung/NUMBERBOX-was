package com.kamcci.numberbox.infra.orm.jpa.adapter.converter.math

import com.kamcci.numberbox.app.domain.enumeration.math.MultiChoiceType
import com.kamcci.numberbox.infra.orm.jpa.adapter.util.CustomAssertUtil.assertDataToEnumMapping
import org.junit.jupiter.api.Test

class MultiChoiceTypeConverterTest {
    private val multiChoiceTypeConverter = MultiChoiceTypeConverter()

    @Test
    fun `to MultiChoiceType`() {
        assertDataToEnumMapping(MultiChoiceType::class.java) { enum ->
            multiChoiceTypeConverter.convertToEntityAttribute(enum?.dbData)
        }
    }
}