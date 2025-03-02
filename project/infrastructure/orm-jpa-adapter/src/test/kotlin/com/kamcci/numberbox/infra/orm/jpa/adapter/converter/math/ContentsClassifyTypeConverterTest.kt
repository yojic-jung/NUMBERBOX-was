package com.kamcci.numberbox.infra.orm.jpa.adapter.converter.math

import com.kamcci.numberbox.app.domain.enumeration.math.ContentsClassifyType
import com.kamcci.numberbox.infra.orm.jpa.adapter.util.CustomAssertUtil.assertDataToEnumMapping
import org.junit.jupiter.api.Test

class ContentsClassifyTypeConverterTest {
    private val contentsClassifyTypeConverter = ContentsClassifyTypeConverter()

    @Test
    fun `to contentsClassifyType`() {
        assertDataToEnumMapping(ContentsClassifyType::class.java) { enum ->
            contentsClassifyTypeConverter.convertToEntityAttribute(enum?.dbData)
        }
    }
}