package com.kamcci.numberbox.infra.orm.jpa.adapter.converter.sys

import com.kamcci.numberbox.app.domain.enumeration.sys.GarbageFileType
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

class GarbageFileTypeConverterTest {
    private val garbageFileTypeConverter = GarbageFileTypeConverter()

    @Test
    fun `dbData to GarbageFileType`() {
        // when
        GarbageFileType.entries.forEach { garbageType ->
            val type = garbageFileTypeConverter.convertToEntityAttribute(garbageType.dbData)

            // then
            Assertions.assertThat(type).isEqualTo(garbageType)
        }
    }

    @Test
    fun `null to null`() {
        // when
        val type = garbageFileTypeConverter.convertToEntityAttribute(null)

        // then
        Assertions.assertThat(type).isEqualTo(null)
    }
}