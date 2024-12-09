package com.kamcci.numberbox.infra.orm.jpa.adapter.converter.math

import com.kamcci.numberbox.app.domain.enumeration.math.IpsiManageInsType
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

class IpsiManageInsTypeConverterTest {
    private val ipsiManageInsTypeConverter = IpsiManageInsTypeConverter()

    @Test
    fun `to IpsiManageInsType`() {
        // given
        val ipsiManageInsTypeList: MutableList<Pair<Int?, IpsiManageInsType?>> = mutableListOf()
        IpsiManageInsType.entries.forEach {
            ipsiManageInsTypeList.add(Pair(it.id, it))
        }
        ipsiManageInsTypeList.add(Pair(null, null))

        // when
        for (ipsiManageInsType in ipsiManageInsTypeList) {
            val type = ipsiManageInsTypeConverter.convertToEntityAttribute(ipsiManageInsType.first)

            // then
            Assertions.assertThat(type).isEqualTo(ipsiManageInsType.second)
        }
    }
}