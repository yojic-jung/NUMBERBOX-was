package com.kamcci.numberbox.infra.orm.jpa.adapter.converter.member

import com.kamcci.numberbox.app.domain.enumeration.member.ProfileType
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

class ProfileTypeConverterTest {
    private val profileTypeConverter = ProfileTypeConverter()

    @Test
    fun `to ProfileType`() {
        // given
        val profileTypeList: MutableList<Pair<Int?, ProfileType?>> = mutableListOf()
        ProfileType.entries.forEach {
            profileTypeList.add(Pair(it.id, it))
        }
        profileTypeList.add(Pair(null, null))

        // when
        for (profileType in profileTypeList) {
            val type = profileTypeConverter.convertToEntityAttribute(profileType.first)

            // then
            Assertions.assertThat(type).isEqualTo(profileType.second)
        }
    }
}