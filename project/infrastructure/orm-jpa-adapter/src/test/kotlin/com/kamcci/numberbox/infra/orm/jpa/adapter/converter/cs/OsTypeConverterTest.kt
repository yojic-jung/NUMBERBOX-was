package com.kamcci.numberbox.infra.orm.jpa.adapter.converter.cs

import com.kamcci.numberbox.app.domain.enumeration.cs.OsType
import com.kamcci.numberbox.infra.orm.jpa.adapter.util.CustomAssertUtil.assertDataToEnumMapping
import org.junit.jupiter.api.Test

class OsTypeConverterTest {
    private val osTypeConverter = OsTypeConverter()

    @Test
    fun `dbData to OsType`() {
        assertDataToEnumMapping(OsType::class.java) { enum ->
            osTypeConverter.convertToEntityAttribute(enum?.dbData)
        }
    }
}