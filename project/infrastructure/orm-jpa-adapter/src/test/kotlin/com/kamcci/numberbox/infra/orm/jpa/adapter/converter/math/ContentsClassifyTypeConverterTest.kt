package com.kamcci.numberbox.infra.orm.jpa.adapter.converter.math

import com.kamcci.numberbox.app.domain.enumeration.math.ContentsClassifyType
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

class ContentsClassifyTypeConverterTest {
    private val contentsClassifyTypeConverter = ContentsClassifyTypeConverter()

    @Test
    fun `to IpsiPaperType`() {
        // given
        val contentsClassifyTypeList: MutableList<Pair<Int?, ContentsClassifyType?>> = mutableListOf()
        ContentsClassifyType.entries.forEach {
            contentsClassifyTypeList.add(Pair(it.id, it))
        }
        contentsClassifyTypeList.add(Pair(null, null))

        // when
        for (contentsSvcPosbSttsType in contentsClassifyTypeList) {
            val type = contentsClassifyTypeConverter.convertToEntityAttribute(contentsSvcPosbSttsType.first)

            // then
            Assertions.assertThat(type).isEqualTo(contentsSvcPosbSttsType.second)
        }
    }
}