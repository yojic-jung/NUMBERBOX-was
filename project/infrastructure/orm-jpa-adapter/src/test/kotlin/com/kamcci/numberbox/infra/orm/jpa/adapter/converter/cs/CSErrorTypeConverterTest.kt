package com.kamcci.numberbox.infra.orm.jpa.adapter.converter.cs

import com.kamcci.numberbox.app.domain.enumeration.cs.CSErrorType
import com.kamcci.numberbox.infra.orm.jpa.adapter.util.CustomAssertUtil.assertDataToEnumMapping
import org.junit.jupiter.api.Test

class CSErrorTypeConverterTest {
    private val csErrorTypeConverter = CSErrorTypeConverter()

    @Test
    fun `dbData to CSErrorType`() {
        assertDataToEnumMapping(CSErrorType::class.java) { enum ->
            csErrorTypeConverter.convertToEntityAttribute(enum?.dbData)
        }
    }
}