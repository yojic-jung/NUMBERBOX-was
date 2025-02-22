package com.kamcci.numberbox.infra.orm.jpa.adapter.converter.member

import com.kamcci.numberbox.app.domain.enumeration.member.VerifyCodeType
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

class VerifyCodeTypeConverterTest {
    private val verifyCodeTypeConverter = VerifyCodeTypeConverter()

    @Test
    fun `dbData to VerifyCodeType`() {
        // when
        VerifyCodeType.entries.forEach { verifyCodeType ->
            val type = verifyCodeTypeConverter.convertToEntityAttribute(verifyCodeType.dbData)

            // then
            Assertions.assertThat(type).isEqualTo(verifyCodeType)
        }
    }

    @Test
    fun `null to null`() {
        // when
        val type = verifyCodeTypeConverter.convertToEntityAttribute(null)

        // then
        Assertions.assertThat(type).isEqualTo(null)
    }
}