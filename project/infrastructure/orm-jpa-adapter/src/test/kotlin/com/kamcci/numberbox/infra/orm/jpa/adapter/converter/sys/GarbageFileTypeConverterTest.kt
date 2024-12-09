package com.kamcci.numberbox.infra.orm.jpa.adapter.converter.sys

import com.kamcci.numberbox.app.domain.enumeration.sys.GarbageFileType
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

class GarbageFileTypeConverterTest {
    private val garbageFileTypeConverter = GarbageFileTypeConverter()

    @Test
    fun `to GarbageFileType`() {
        // given
        val garbageFileTypeList: MutableList<Pair<String?, GarbageFileType?>> = mutableListOf()
        GarbageFileType.entries.forEach {
            garbageFileTypeList.add(Pair(it.dbData, it))
        }
        garbageFileTypeList.add(Pair(null, null))

        // when
        for (garbageFileType in garbageFileTypeList) {
            val type = garbageFileTypeConverter.convertToEntityAttribute(garbageFileType.first)

            // then
            Assertions.assertThat(type).isEqualTo(garbageFileType.second)
        }
    }
}