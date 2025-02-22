package com.kamcci.numberbox.infra.orm.jpa.adapter.converter.member

import com.kamcci.numberbox.app.domain.enumeration.member.ProfileType
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

class ProfileTypeConverterTest {
    private val profileTypeConverter = ProfileTypeConverter()

    @Test
    fun `dbData to ProfileType`() {
        // when
        ProfileType.entries.forEach { profileType ->
            val type = profileTypeConverter.convertToEntityAttribute(profileType.dbData)

            // then
            Assertions.assertThat(type).isEqualTo(profileType)
        }
    }

    @Test
    fun `null to null`() {
        // when
        val type = profileTypeConverter.convertToEntityAttribute(null)

        // then
        Assertions.assertThat(type).isEqualTo(null)
    }
}