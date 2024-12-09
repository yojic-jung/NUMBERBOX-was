package com.kamcci.numberbox.infra.orm.jpa.adapter.converter.member

import com.kamcci.numberbox.app.domain.enumeration.member.VerifyCodeType
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

class VerifyCodeTypeConverterTest {
    private val verifyCodeTypeConverter = VerifyCodeTypeConverter()

    @Test
    fun `to VerifyCodeType`() {
        // given
        val verifyCodeTypeList: MutableList<Pair<Int?, VerifyCodeType?>> = mutableListOf()
        VerifyCodeType.entries.forEach {
            verifyCodeTypeList.add(Pair(it.id, it))
        }
        verifyCodeTypeList.add(Pair(null, null))

        // when
        for (verifyCodeType in verifyCodeTypeList) {
            val type = verifyCodeTypeConverter.convertToEntityAttribute(verifyCodeType.first)

            // then
            Assertions.assertThat(type).isEqualTo(verifyCodeType.second)
        }
    }
}