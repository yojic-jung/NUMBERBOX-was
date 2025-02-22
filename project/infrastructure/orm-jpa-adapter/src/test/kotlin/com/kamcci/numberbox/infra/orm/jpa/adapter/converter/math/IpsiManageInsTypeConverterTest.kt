package com.kamcci.numberbox.infra.orm.jpa.adapter.converter.math

import com.kamcci.numberbox.app.domain.enumeration.math.IpsiManageInsType
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

class IpsiManageInsTypeConverterTest {
    private val ipsiManageInsTypeConverter = IpsiManageInsTypeConverter()

    @Test
    fun `to IpsiManageInsType`() {
        // when
        IpsiManageInsType.entries.forEach { ipsiManageInsType ->
            val type = ipsiManageInsTypeConverter.convertToEntityAttribute(ipsiManageInsType.dbData)

            // then
            Assertions.assertThat(type).isEqualTo(ipsiManageInsType)
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