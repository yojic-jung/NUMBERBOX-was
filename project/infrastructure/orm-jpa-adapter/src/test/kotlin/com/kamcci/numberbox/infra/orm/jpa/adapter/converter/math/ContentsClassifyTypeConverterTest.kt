package com.kamcci.numberbox.infra.orm.jpa.adapter.converter.math

import com.kamcci.numberbox.app.domain.enumeration.math.ContentsClassifyType
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

class ContentsClassifyTypeConverterTest {
    private val contentsClassifyTypeConverter = ContentsClassifyTypeConverter()

    @Test
    fun `to contentsClassifyType`() {
        // when
        ContentsClassifyType.entries.forEach { contentsClassifyType ->
            val type = contentsClassifyTypeConverter.convertToEntityAttribute(contentsClassifyType.dbData)

            // then
            Assertions.assertThat(type).isEqualTo(contentsClassifyType)
        }
    }

    @Test
    fun `null to null`() {
        // when
        val type = contentsClassifyTypeConverter.convertToEntityAttribute(null)

        // then
        Assertions.assertThat(type).isEqualTo(null)
    }
}