package com.kamcci.numberbox.infra.orm.jpa.adapter.converter.sys

import com.kamcci.numberbox.app.domain.enumeration.sys.GarbageFileType
import com.kamcci.numberbox.infra.orm.jpa.adapter.util.CustomAssertUtil
import org.junit.jupiter.api.Test

class GarbageFileTypeConverterTest {
    private val garbageFileTypeConverter = GarbageFileTypeConverter()

    @Test
    fun `dbData to GarbageFileType`() {
        CustomAssertUtil.assertDataToEnumMapping(GarbageFileType::class.java) { enum ->
            garbageFileTypeConverter.convertToEntityAttribute(enum?.dbData)
        }
    }
}