package com.kamcci.numberbox.infra.orm.jpa.adapter.converter.math

import com.kamcci.numberbox.app.domain.enumeration.math.IpsiManageInsType
import com.kamcci.numberbox.infra.orm.jpa.adapter.util.CustomAssertUtil.assertDataToEnumMapping
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

class IpsiManageInsTypeConverterTest {
    private val ipsiManageInsTypeConverter = IpsiManageInsTypeConverter()

    @Test
    fun `to IpsiManageInsType`() {
        assertDataToEnumMapping(IpsiManageInsType::class.java) { enum ->
            ipsiManageInsTypeConverter.convertToEntityAttribute(enum?.dbData)
        }
    }

    @Test
    fun `null to null`() {
        // when
        val type = ipsiManageInsTypeConverter.convertToEntityAttribute(null)

        // then
        Assertions.assertThat(type).isEqualTo(null)
    }
}