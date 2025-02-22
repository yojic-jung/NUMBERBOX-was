package com.kamcci.numberbox.infra.orm.jpa.adapter.converter.math

import com.kamcci.numberbox.app.domain.enumeration.math.ContentsSvcPosbSttsType
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class ContentsSvcPosbSttsTypeConverterTest {
    private val contentsSvcPosbSttsTypeConverter = ContentsSvcPosbSttsTypeConverter()

    @Test
    fun `dbData to ContentsSvcPosbSttsType`() {
        // when
        ContentsSvcPosbSttsType.entries.forEach { contentsSvcPosbSttsType ->
            val type = contentsSvcPosbSttsTypeConverter.convertToEntityAttribute(contentsSvcPosbSttsType.dbData)

            // then
            assertThat(type).isEqualTo(contentsSvcPosbSttsType)
        }
    }

    @Test
    fun `null to null`() {
        // when
        val type = contentsSvcPosbSttsTypeConverter.convertToEntityAttribute(null)

        // then
        assertThat(type).isEqualTo(null)
    }
}