package com.kamcci.numberbox.infra.orm.jpa.adapter.converter.cs

import com.kamcci.numberbox.app.domain.enumeration.cs.BrowserType
import com.kamcci.numberbox.infra.orm.jpa.adapter.util.CustomAssertUtil.assertDataToEnumMapping
import org.junit.jupiter.api.Test

class BrowserTypeConverterTest {
    private val browserTypeConverter = BrowserTypeConverter()

    @Test
    fun `dbData to BrowserType`() {
        assertDataToEnumMapping(BrowserType::class.java) { enum ->
            browserTypeConverter.convertToEntityAttribute(enum?.dbData)
        }
    }
}