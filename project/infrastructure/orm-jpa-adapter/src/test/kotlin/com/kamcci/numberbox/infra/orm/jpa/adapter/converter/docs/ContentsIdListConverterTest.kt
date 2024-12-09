package com.kamcci.numberbox.infra.orm.jpa.adapter.converter.docs

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class ContentsIdListConverterTest {
    private val contentsIdListConverter = ContentsIdListConverter()

    @Test
    fun `to List`() {
        // when
        val list = contentsIdListConverter.convertToEntityAttribute("1,2,3,4,5")

        // then
        assertThat(list.size).isEqualTo(5)
        assertThat(list.contains(1L)).isTrue()
        assertThat(list.contains(2L)).isTrue()
        assertThat(list.contains(3L)).isTrue()
        assertThat(list.contains(4L)).isTrue()
        assertThat(list.contains(5L)).isTrue()
    }

    @Test
    fun `null to Null`() {
        // given
        val list = contentsIdListConverter.convertToEntityAttribute(null)

        // then
        assertThat(list).isEmpty()
    }

}