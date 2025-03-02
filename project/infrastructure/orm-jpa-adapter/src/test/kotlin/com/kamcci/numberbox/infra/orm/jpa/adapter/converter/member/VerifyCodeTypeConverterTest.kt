package com.kamcci.numberbox.infra.orm.jpa.adapter.converter.member

import com.kamcci.numberbox.app.domain.enumeration.member.VerifyCodeType
import com.kamcci.numberbox.infra.orm.jpa.adapter.util.CustomAssertUtil.assertDataToEnumMapping
import org.junit.jupiter.api.Test

class VerifyCodeTypeConverterTest {
    private val verifyCodeTypeConverter = VerifyCodeTypeConverter()

    @Test
    fun `dbData to VerifyCodeType`() {
        assertDataToEnumMapping(VerifyCodeType::class.java) { enum ->
            verifyCodeTypeConverter.convertToEntityAttribute(enum?.dbData)
        }
    }
}