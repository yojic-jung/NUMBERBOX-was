package com.kamcci.numberbox.infra.orm.jpa.adapter.converter.math

import com.kamcci.numberbox.app.domain.enumeration.math.IpsiPaperType
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

class IpsiPaperTypeConverterTest {
    private val ipsiPaperTypeConverter = IpsiPaperTypeConverter()

    @Test
    fun `to IpsiPaperType`() {
        // when
        IpsiPaperType.entries.forEach { ipsiPaperType ->
            val type = ipsiPaperTypeConverter.convertToEntityAttribute(ipsiPaperType.dbData)

            // then
            Assertions.assertThat(type).isEqualTo(ipsiPaperType)
        }
    }

    @Test
    fun `null to null`() {
        // when
        val type = ipsiPaperTypeConverter.convertToEntityAttribute(null)

        // then
        Assertions.assertThat(type).isEqualTo(null)
    }
}