package com.kamcci.numberbox.infra.orm.jpa.adapter.converter.cs

import com.kamcci.numberbox.app.domain.enumeration.cs.BrowserType
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class BrowserTypeConverterTest {
    private val browserTypeConverter = BrowserTypeConverter()

    @Test
    fun `to BrowserType`() {
        // given
        val browserTypeList: MutableList<Pair<String?, BrowserType?>> = mutableListOf()
        BrowserType.entries.forEach {
            browserTypeList.add(Pair(it.dbData, it))
        }
        browserTypeList.add(Pair(null, null))

        // when
        for (browserType in browserTypeList) {
            val type = browserTypeConverter.convertToEntityAttribute(browserType.first)

            // then
            assertThat(type).isEqualTo(browserType.second)
        }
    }
}