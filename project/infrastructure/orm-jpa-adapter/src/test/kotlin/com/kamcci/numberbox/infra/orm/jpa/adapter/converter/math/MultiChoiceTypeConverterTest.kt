package com.kamcci.numberbox.infra.orm.jpa.adapter.converter.math

import com.kamcci.numberbox.app.domain.enumeration.math.MultiChoiceType
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

class MultiChoiceTypeConverterTest {
    private val multiChoiceTypeConverter = MultiChoiceTypeConverter()

    @Test
    fun `to MultiChoiceType`() {
        // given
        val multiChoiceTypeList: MutableList<Pair<String?, MultiChoiceType?>> = mutableListOf()
        MultiChoiceType.entries.forEach {
            multiChoiceTypeList.add(Pair(it.id, it))
        }
        multiChoiceTypeList.add(Pair(null, null))

        // when
        for (multiChoiceType in multiChoiceTypeList) {
            val type = multiChoiceTypeConverter.convertToEntityAttribute(multiChoiceType.first)

            // then
            Assertions.assertThat(type).isEqualTo(multiChoiceType.second)
        }
    }
}