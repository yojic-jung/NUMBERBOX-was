package com.kamcci.numberbox.infra.orm.jpa.adapter.converter.math

import com.kamcci.numberbox.app.domain.enumeration.math.ContentsSvcPosbSttsType
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class ContentsSvcPosbSttsTypeConverterTest {
    private val contentsSvcPosbSttsTypeConverter = ContentsSvcPosbSttsTypeConverter()

    @Test
    fun `to IpsiPaperType`() {
        // given
        val contentsSvcPosbSttsTypeList: MutableList<Pair<Int?, ContentsSvcPosbSttsType?>> = mutableListOf()
        ContentsSvcPosbSttsType.entries.forEach {
            contentsSvcPosbSttsTypeList.add(Pair(it.id, it))
        }
        contentsSvcPosbSttsTypeList.add(Pair(null, null))

        // when
        for (contentsSvcPosbSttsType in contentsSvcPosbSttsTypeList) {
            val type = contentsSvcPosbSttsTypeConverter.convertToEntityAttribute(contentsSvcPosbSttsType.first)

            // then
            assertThat(type).isEqualTo(contentsSvcPosbSttsType.second)
        }
    }
}