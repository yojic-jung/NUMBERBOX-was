package com.kamcci.numberbox.infra.orm.jpa.adapter.converter.math

import com.kamcci.numberbox.app.domain.enumeration.math.MultiChoiceType
import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter

@Converter(autoApply = true)
class MultiChoiceTypeConverter : AttributeConverter<MultiChoiceType, String> {
    override fun convertToEntityAttribute(column: String?): MultiChoiceType? {
        return MultiChoiceType.entries.find { it.id == column }
    }

    override fun convertToDatabaseColumn(property: MultiChoiceType): String {
        return property.id
    }
}