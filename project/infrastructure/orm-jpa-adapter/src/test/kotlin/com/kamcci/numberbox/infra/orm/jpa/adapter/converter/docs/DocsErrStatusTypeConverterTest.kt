package com.kamcci.numberbox.infra.orm.jpa.adapter.converter.docs

import com.kamcci.numberbox.app.domain.enumeration.docs.DocsStatusType
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class DocsErrStatusTypeConverterTest {
    private val docsErrStatusTypeConverter = DocsErrStatusTypeConverter()

    @Test
    fun `to DocsStatusType`() {
        // given
        val docsStatusTypeList: MutableList<Pair<Int?, DocsStatusType?>> = mutableListOf()
        DocsStatusType.entries.forEach {
            docsStatusTypeList.add(Pair(it.id, it))
        }
        docsStatusTypeList.add(Pair(null, null))

        // when
        for (docsStatusType in docsStatusTypeList) {
            val type = docsErrStatusTypeConverter.convertToEntityAttribute(docsStatusType.first)

            // then
            assertThat(type).isEqualTo(docsStatusType.second)
        }
    }
}