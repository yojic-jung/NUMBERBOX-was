package com.kamcci.numberbox.infra.orm.converter

import com.kamcci.numberbox.app.domain.enumeration.member.ProfileType
import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter

@Converter(autoApply = true)
class ProfileTypeConverter : AttributeConverter<ProfileType, Int> {
    override fun convertToEntityAttribute(column: Int): ProfileType? {
        return ProfileType.entries.find { it.id == column }
    }

    override fun convertToDatabaseColumn(property: ProfileType): Int {
        return property.id
    }
}