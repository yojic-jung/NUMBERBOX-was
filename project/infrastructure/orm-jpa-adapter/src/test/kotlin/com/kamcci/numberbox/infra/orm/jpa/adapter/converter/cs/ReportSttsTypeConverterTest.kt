package com.kamcci.numberbox.infra.orm.jpa.adapter.converter.cs

import com.kamcci.numberbox.app.domain.enumeration.cs.ReportSttsType
import com.kamcci.numberbox.infra.orm.jpa.adapter.util.CustomAssertUtil.assertDataToEnumMapping
import org.junit.jupiter.api.Test

class ReportSttsTypeConverterTest {
    private val reportSttsTypeConverter = ReportSttsTypeConverter()

    @Test
    fun `dbData to ReportSttsType`() {
        assertDataToEnumMapping(ReportSttsType::class.java) { enum ->
            reportSttsTypeConverter.convertToEntityAttribute(enum?.dbData)
        }
    }
}