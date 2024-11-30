package com.kamcci.numberbox.infra.orm.jpa.adapter.converter.math

import com.kamcci.numberbox.app.domain.enumeration.math.ContentsSvcPosbSttsType
import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter

@Converter(autoApply = true)
class ContentsSvcPosbSttsTypeConverter : AttributeConverter<ContentsSvcPosbSttsType, Int> {
    override fun convertToEntityAttribute(column: Int?): ContentsSvcPosbSttsType? {
        return ContentsSvcPosbSttsType.entries.find { it.id == column }
    }

    override fun convertToDatabaseColumn(property: ContentsSvcPosbSttsType): Int {
        return property.id
    }
}