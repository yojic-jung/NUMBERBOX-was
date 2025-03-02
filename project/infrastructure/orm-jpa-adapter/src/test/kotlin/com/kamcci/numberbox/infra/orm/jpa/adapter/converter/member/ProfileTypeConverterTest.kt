package com.kamcci.numberbox.infra.orm.jpa.adapter.converter.member

import com.kamcci.numberbox.app.domain.enumeration.member.ProfileType
import com.kamcci.numberbox.infra.orm.jpa.adapter.util.CustomAssertUtil.assertDataToEnumMapping
import org.junit.jupiter.api.Test

class ProfileTypeConverterTest {
    private val profileTypeConverter = ProfileTypeConverter()

    @Test
    fun `dbData to ProfileType`() {
        assertDataToEnumMapping(ProfileType::class.java) { enum ->
            profileTypeConverter.convertToEntityAttribute(enum?.dbData)
        }
    }
}