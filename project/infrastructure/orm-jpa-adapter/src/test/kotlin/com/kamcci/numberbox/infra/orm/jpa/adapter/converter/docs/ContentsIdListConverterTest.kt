package com.kamcci.numberbox.infra.orm.jpa.adapter.converter.docs

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class ContentsIdListConverterTest {
    private val contentsIdListConverter = ContentsIdListConverter()

    @Test
    fun `dbData to contentIdList`() {
        val dbData = "1,2,3,4,5"

        // when
        val list = contentsIdListConverter.convertToEntityAttribute(dbData)

        // then
        assertThat(list.size).isEqualTo(5)
        list.forEach {
            assertThat(dbData).contains(it.toString())
        }
    }

    @Test
    fun `null to empty`() {
        // given
        val list = contentsIdListConverter.convertToEntityAttribute(null)

        // then
        assertThat(list).isEmpty()
    }

}