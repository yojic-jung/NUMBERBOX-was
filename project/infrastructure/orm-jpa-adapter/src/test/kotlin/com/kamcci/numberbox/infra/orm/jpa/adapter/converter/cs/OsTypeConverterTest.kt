package com.kamcci.numberbox.infra.orm.jpa.adapter.converter.cs

import com.kamcci.numberbox.app.domain.enumeration.cs.OsType
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

class OsTypeConverterTest {
    private val osTypeConverter = OsTypeConverter()

    @Test
    fun `dbData to BrowserType`() {
        // when
        OsType.entries.forEach { osType ->
            val type = osTypeConverter.convertToEntityAttribute(osType.dbData)

            // then
            Assertions.assertThat(type).isEqualTo(osType)
        }
    }

    @Test
    fun `null to null`() {
        // when
        val type = osTypeConverter.convertToEntityAttribute(null)

        // then
        Assertions.assertThat(type).isEqualTo(null)
    }
}