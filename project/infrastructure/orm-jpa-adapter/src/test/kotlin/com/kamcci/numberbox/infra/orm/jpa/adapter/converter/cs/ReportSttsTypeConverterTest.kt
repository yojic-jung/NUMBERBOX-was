package com.kamcci.numberbox.infra.orm.jpa.adapter.converter.cs

import com.kamcci.numberbox.app.domain.enumeration.cs.ReportSttsType
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class ReportSttsTypeConverterTest {
    private val reportSttsTypeConverter = ReportSttsTypeConverter()

    @Test
    fun `dbData to ReportSttsType`() {
        // when
        ReportSttsType.entries.forEach { reportSttsType ->
            val type = reportSttsTypeConverter.convertToEntityAttribute(reportSttsType.dbData)

            // then
            assertThat(type).isEqualTo(reportSttsType)
        }
    }

    @Test
    fun `null to null`() {
        // when
        val type = reportSttsTypeConverter.convertToEntityAttribute(null)

        // then
        assertThat(type).isEqualTo(null)
    }

}