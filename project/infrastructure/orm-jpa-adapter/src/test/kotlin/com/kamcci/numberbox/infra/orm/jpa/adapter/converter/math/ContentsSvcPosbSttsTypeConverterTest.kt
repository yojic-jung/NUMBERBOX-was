package com.kamcci.numberbox.infra.orm.jpa.adapter.converter.math

import com.kamcci.numberbox.app.domain.enumeration.math.ContentsSvcPosbSttsType
import com.kamcci.numberbox.infra.orm.jpa.adapter.util.CustomAssertUtil.assertDataToEnumMapping
import org.junit.jupiter.api.Test

class ContentsSvcPosbSttsTypeConverterTest {
    private val contentsSvcPosbSttsTypeConverter = ContentsSvcPosbSttsTypeConverter()

    @Test
    fun `dbData to ContentsSvcPosbSttsType`() {
        assertDataToEnumMapping(ContentsSvcPosbSttsType::class.java) { enum ->
            contentsSvcPosbSttsTypeConverter.convertToEntityAttribute(enum?.dbData)
        }
    }
}