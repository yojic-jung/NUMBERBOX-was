package com.kamcci.numberbox.infra.orm.jpa.adapter.converter.cs

import com.kamcci.numberbox.app.domain.enumeration.cs.ReportSttsType
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class ReportSttsTypeConverterTest {
    private val reportSttsTypeConverter = ReportSttsTypeConverter()

    @Test
    fun `to ReportSttsType`() {
        // given
        val reportSttsTypeList: MutableList<Pair<Int?, ReportSttsType?>> = mutableListOf()
        ReportSttsType.entries.forEach {
            reportSttsTypeList.add(Pair(it.dbData, it))
        }
        reportSttsTypeList.add(Pair(null, null))

        // when
        for (reportSttsType in reportSttsTypeList) {
            val type = reportSttsTypeConverter.convertToEntityAttribute(reportSttsType.first)

            // then
            assertThat(type).isEqualTo(reportSttsType.second)
        }
    }

}