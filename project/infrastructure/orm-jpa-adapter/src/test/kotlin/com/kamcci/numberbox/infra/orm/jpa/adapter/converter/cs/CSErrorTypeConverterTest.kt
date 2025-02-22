package com.kamcci.numberbox.infra.orm.jpa.adapter.converter.cs

import com.kamcci.numberbox.app.domain.enumeration.cs.CSErrorType
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

class CSErrorTypeConverterTest {
    private val csErrorTypeConverter = CSErrorTypeConverter()

    @Test
    fun `dbData to CSErrorType`() {
        // when
        CSErrorType.entries.forEach { csErrorType ->
            val type = csErrorTypeConverter.convertToEntityAttribute(csErrorType.dbData)

            // then
            Assertions.assertThat(type).isEqualTo(csErrorType)
        }
    }

    @Test
    fun `null to null`() {
        // when
        val type = csErrorTypeConverter.convertToEntityAttribute(null)

        // then
        Assertions.assertThat(type).isEqualTo(null)
    }
}