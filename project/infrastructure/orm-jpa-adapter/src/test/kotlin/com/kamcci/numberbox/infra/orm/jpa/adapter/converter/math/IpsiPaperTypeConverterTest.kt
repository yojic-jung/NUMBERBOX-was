package com.kamcci.numberbox.infra.orm.jpa.adapter.converter.math

import com.kamcci.numberbox.app.domain.enumeration.math.IpsiPaperType
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

class IpsiPaperTypeConverterTest {
    private val ipsiPaperTypeConverter = IpsiPaperTypeConverter()

    @Test
    fun `to ContentsSvcPosbSttsType`() {
        // given
        val ipsiPaperTypeList: MutableList<Pair<Int?, IpsiPaperType?>> = mutableListOf()
        IpsiPaperType.entries.forEach {
            ipsiPaperTypeList.add(Pair(it.id, it))
        }
        ipsiPaperTypeList.add(Pair(null, null))

        // when
        for (ipsiPaperType in ipsiPaperTypeList) {
            val type = ipsiPaperTypeConverter.convertToEntityAttribute(ipsiPaperType.first)

            // then
            Assertions.assertThat(type).isEqualTo(ipsiPaperType.second)
        }
    }
}