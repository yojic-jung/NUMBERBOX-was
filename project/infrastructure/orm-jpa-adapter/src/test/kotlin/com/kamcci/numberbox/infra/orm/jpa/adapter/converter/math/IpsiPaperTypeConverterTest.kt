package com.kamcci.numberbox.infra.orm.jpa.adapter.converter.math

import com.kamcci.numberbox.app.domain.enumeration.math.IpsiPaperType
import com.kamcci.numberbox.infra.orm.jpa.adapter.util.CustomAssertUtil.assertDataToEnumMapping
import org.junit.jupiter.api.Test

class IpsiPaperTypeConverterTest {
    private val ipsiPaperTypeConverter = IpsiPaperTypeConverter()

    @Test
    fun `to IpsiPaperType`() {
        assertDataToEnumMapping(IpsiPaperType::class.java) { enum ->
            ipsiPaperTypeConverter.convertToEntityAttribute(enum?.dbData)
        }
    }
}