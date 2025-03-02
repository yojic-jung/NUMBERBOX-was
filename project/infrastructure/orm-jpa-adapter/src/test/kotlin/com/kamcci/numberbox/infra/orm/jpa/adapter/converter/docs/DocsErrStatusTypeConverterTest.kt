package com.kamcci.numberbox.infra.orm.jpa.adapter.converter.docs

import com.kamcci.numberbox.app.domain.enumeration.docs.DocsStatusType
import com.kamcci.numberbox.infra.orm.jpa.adapter.util.CustomAssertUtil.assertDataToEnumMapping
import org.junit.jupiter.api.Test

class DocsErrStatusTypeConverterTest {
    private val docsErrStatusTypeConverter = DocsErrStatusTypeConverter()

    @Test
    fun `dbData to DocsStatusType`() {
        assertDataToEnumMapping(DocsStatusType::class.java) { enum ->
            docsErrStatusTypeConverter.convertToEntityAttribute(enum?.dbData)
        }
    }
}
