package com.kamcci.numberbox.infra.orm.jpa.adapter.converter.cs

import com.kamcci.numberbox.app.domain.enumeration.cs.BrowserType
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class BrowserTypeConverterTest {
    private val browserTypeConverter = BrowserTypeConverter()

    @Test
    fun `dbData to BrowserType`() {
        // when
        BrowserType.entries.forEach { browserType ->
            val type = browserTypeConverter.convertToEntityAttribute(browserType.dbData)

            // then
            assertThat(type).isEqualTo(browserType)
        }
    }

    @Test
    fun `null to null`() {
        // when
        val type = browserTypeConverter.convertToEntityAttribute(null)

        // then
        assertThat(type).isEqualTo(null)
    }
}