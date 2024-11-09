package com.kamcci.numberbox.infra.orm.converter.cs

import com.kamcci.numberbox.app.domain.enumeration.cs.CSErrorType
import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter

@Converter(autoApply = true)
class CSErrorTypeConverter : AttributeConverter<CSErrorType, Int> {
    // LeftJoin으로 null 반환 가능
    override fun convertToEntityAttribute(column: Int): CSErrorType? {
        return CSErrorType.entries.find { it.id == column }
    }

    override fun convertToDatabaseColumn(property: CSErrorType): Int {
        return property.id
    }
}