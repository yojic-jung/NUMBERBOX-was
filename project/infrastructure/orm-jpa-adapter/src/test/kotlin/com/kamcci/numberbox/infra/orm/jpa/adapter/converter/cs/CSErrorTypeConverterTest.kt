package com.kamcci.numberbox.infra.orm.jpa.adapter.converter.cs

import com.kamcci.numberbox.app.domain.enumeration.cs.CSErrorType
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

class CSErrorTypeConverterTest {
    private val csErrorTypeConverter = CSErrorTypeConverter()

    @Test
    fun `to CSErrorType`() {
        // given
        val csErrorTypeList: MutableList<Pair<Int?, CSErrorType?>> = mutableListOf()
        CSErrorType.entries.forEach {
            csErrorTypeList.add(Pair(it.dbData, it))
        }
        csErrorTypeList.add(Pair(null, null))

        // when
        for (csErrorType in csErrorTypeList) {
            val type = csErrorTypeConverter.convertToEntityAttribute(csErrorType.first)

            // then
            Assertions.assertThat(type).isEqualTo(csErrorType.second)
        }
    }
}