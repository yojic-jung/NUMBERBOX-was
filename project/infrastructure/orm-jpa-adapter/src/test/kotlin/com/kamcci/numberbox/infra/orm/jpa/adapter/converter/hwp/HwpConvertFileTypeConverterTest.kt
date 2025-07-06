package com.kamcci.numberbox.infra.orm.jpa.adapter.converter.hwp

import com.kamcci.numberbox.app.domain.enumeration.hwp.HwpConvertFileType
import com.kamcci.numberbox.infra.orm.jpa.adapter.util.CustomAssertUtil
import org.junit.jupiter.api.Test

class HwpConvertFileTypeConverterTest {
    private val hwpConvertFileTypeConverter = HwpConvertFileTypeConverter()

    @Test
    fun `dbData to HwpConvertFileType`() {
        CustomAssertUtil.assertDataToEnumMapping(HwpConvertFileType::class.java) { enum ->
            hwpConvertFileTypeConverter.convertToEntityAttribute(enum?.dbData)
        }
    }

}