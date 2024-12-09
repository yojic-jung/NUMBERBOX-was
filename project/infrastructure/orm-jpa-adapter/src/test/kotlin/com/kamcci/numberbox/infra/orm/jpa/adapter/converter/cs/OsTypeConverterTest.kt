package com.kamcci.numberbox.infra.orm.jpa.adapter.converter.cs

import com.kamcci.numberbox.app.domain.enumeration.cs.OsType
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

class OsTypeConverterTest {
    private val osTypeConverter = OsTypeConverter()

    @Test
    fun `to BrowserType`() {
        // given
        val osTypeList: MutableList<Pair<String?, OsType?>> = mutableListOf()
        OsType.entries.forEach {
            osTypeList.add(Pair(it.id, it))
        }
        osTypeList.add(Pair(null, null))

        // when
        for (osType in osTypeList) {
            val type = osTypeConverter.convertToEntityAttribute(osType.first)

            // then
            Assertions.assertThat(type).isEqualTo(osType.second)
        }
    }
}