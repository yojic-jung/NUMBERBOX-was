package com.kamcci.numberbox.infra.orm.jpa.adapter.converter.docs

import com.kamcci.numberbox.app.domain.enumeration.docs.DocsStatusType
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class DocsErrStatusTypeConverterTest {
    private val docsErrStatusTypeConverter = DocsErrStatusTypeConverter()

    @Test
    fun `dbData to DocsStatusType`() {
        // when
        DocsStatusType.entries.forEach { docsStatusType ->
            val type = docsErrStatusTypeConverter.convertToEntityAttribute(docsStatusType.dbData)

            // then
            assertThat(type).isEqualTo(docsStatusType)
        }
    }

    @Test
    fun `null to null`() {
        // when
        val type = docsErrStatusTypeConverter.convertToEntityAttribute(null)

        // then
        assertThat(type).isEqualTo(null)
    }
}
